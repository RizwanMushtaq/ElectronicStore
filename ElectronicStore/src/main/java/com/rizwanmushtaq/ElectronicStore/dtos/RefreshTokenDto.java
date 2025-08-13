package com.rizwanmushtaq.ElectronicStore.dtos;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenDto {
  private int id;
  private String token;
  private Instant expiryDate;
}
