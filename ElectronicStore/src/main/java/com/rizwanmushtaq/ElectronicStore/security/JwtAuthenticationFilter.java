package com.rizwanmushtaq.ElectronicStore.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
  @Autowired
  private JwtHelper jwtHelper;
  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    /*
     * This Code will run before any controller access, to verify JWT
     */
    // Authorization : 'Bearer Token'
    String requestHeader = request.getHeader("Authorization");
    logger.info("Request Header {}", requestHeader);
    if (requestHeader == null && !requestHeader.startsWith("Bearer")) {
      logger.info("Invalid Header");
      return;
    }
    String username = null;
    String token = requestHeader.substring(7);
    try {
      username = jwtHelper.getUsernameFromToken(token);
      logger.info("username got from token {}", username);
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails =
            userDetailsService.loadUserByUsername(username);
        if (username.equals(userDetails.getUsername()) && !jwtHelper.isTokenExpired(token)) {
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
      filterChain.doFilter(request, response);
    } catch (IllegalArgumentException exception) {
      logger.info("Illegal Argument Exception while fetching username: {}",
          exception.getMessage());
    } catch (ExpiredJwtException exception) {
      logger.info("Expired Jwt: {}", exception.getMessage());
    } catch (MalformedJwtException exception) {
      logger.info("Jwt is Malformed: {}", exception.getMessage());
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
