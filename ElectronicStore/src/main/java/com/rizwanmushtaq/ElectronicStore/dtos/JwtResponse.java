package com.rizwanmushtaq.ElectronicStore.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
  UserDto user;
  private String token;
  private String refreshToken;
}
