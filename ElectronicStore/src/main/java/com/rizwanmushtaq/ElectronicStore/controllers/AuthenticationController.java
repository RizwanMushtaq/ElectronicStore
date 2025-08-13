package com.rizwanmushtaq.ElectronicStore.controllers;

import com.rizwanmushtaq.ElectronicStore.dtos.*;
import com.rizwanmushtaq.ElectronicStore.entities.User;
import com.rizwanmushtaq.ElectronicStore.security.JwtHelper;
import com.rizwanmushtaq.ElectronicStore.services.RefreshTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth Management", description = "Operations related to managing user authentication in the E-Store")
public class AuthenticationController {
  private Logger logger =
      LoggerFactory.getLogger(AuthenticationController.class);
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtHelper jwtHelper;
  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private RefreshTokenService refreshTokenService;

  @PostMapping("/regenerate-token")
  public ResponseEntity<JwtResponse> regenerateToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
    RefreshTokenDto refreshTokenDto =
        refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken());
    RefreshTokenDto verifiedRefreshToken =
        refreshTokenService.verifyRefreshToken(refreshTokenDto);
    UserDto user = refreshTokenService.getUser(verifiedRefreshToken);
    String jwtToken = jwtHelper.generateToken(modelMapper.map(user,
        User.class));
    JwtResponse jwtResponse = JwtResponse
        .builder()
        .token(jwtToken)
        .refreshToken(verifiedRefreshToken.getToken())
        .user(user)
        .build();
    return ResponseEntity.ok(jwtResponse);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
    logger.info("Username {}, Password {}", request.getUsername(), request.getPassword());
    this.doAuthenticate(request.getUsername(), request.getPassword());
    User user = (User) userDetailsService.loadUserByUsername(request.getUsername());
    String token = jwtHelper.generateToken(user);
    RefreshTokenDto refreshTokenDto =
        refreshTokenService.createRefreshToken(user.getUsername());
    JwtResponse jwtResponse = JwtResponse
        .builder()
        .token(token)
        .refreshToken(refreshTokenDto.getToken())
        .user(modelMapper.map(user, UserDto.class))
        .build();
    return ResponseEntity.ok(jwtResponse);
  }

  private void doAuthenticate(String username, String password) {
    try {
      Authentication authentication =
          new UsernamePasswordAuthenticationToken(username, password);
      authenticationManager.authenticate(authentication);
    } catch (BadCredentialsException exception) {
      throw new BadCredentialsException("Invalid Username or Password!");
    }
  }
}
