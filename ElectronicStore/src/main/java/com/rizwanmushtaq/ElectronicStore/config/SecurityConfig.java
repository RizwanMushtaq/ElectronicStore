package com.rizwanmushtaq.ElectronicStore.config;

import com.rizwanmushtaq.ElectronicStore.security.JwtAuthenticationEntryPoint;
import com.rizwanmushtaq.ElectronicStore.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity(debug = false)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(
        new CorsConfigurationSource() {
          @Override
          public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(List.of(
                "http://localhost:4200",
                "http://localhost:5173",
                "http://localhost"
            ));
            corsConfiguration.setAllowedMethods(List.of("*"));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setAllowedHeaders(List.of("*"));
            corsConfiguration.setMaxAge(3000L);
            return corsConfiguration;
          }
        }
    ));
    // disable CSRF as we are using JWT based authentication
    httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
    // Configure Urls
    httpSecurity.authorizeHttpRequests(request ->
        request
            .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
            .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole(AppConstants.ROLE_ADMIN)
            .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole(AppConstants.ROLE_ADMIN, AppConstants.ROLE_NORMAL)
            .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
            .requestMatchers("/api/products/**").hasRole(AppConstants.ROLE_ADMIN)
            .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
            .requestMatchers("/api/categories/**").hasRole(AppConstants.ROLE_ADMIN)
            .requestMatchers(HttpMethod.POST, "/api/auth/login",
                "/api/auth/regenerate-token").permitAll()
            .requestMatchers("/api/auth/**").authenticated()
            .anyRequest().permitAll()
    );
    // Configure Type of Security
    // httpSecurity.httpBasic(Customizer.withDefaults());
    httpSecurity.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint));
    httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
