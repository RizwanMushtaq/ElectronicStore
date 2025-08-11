package com.rizwanmushtaq.ElectronicStore.controllers;

import com.rizwanmushtaq.ElectronicStore.dtos.JwtRequest;
import com.rizwanmushtaq.ElectronicStore.dtos.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
  private Logger logger =
      LoggerFactory.getLogger(AuthenticationController.class);

  @PostMapping("/generate-token")
  public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
    logger.info("Username {}, Password {}", request.getUsername(), request.getPassword());
    return ResponseEntity.ok(null);
  }
}
