package com.rizwanmushtaq.ElectronicStore.services;

import com.rizwanmushtaq.ElectronicStore.dtos.RefreshTokenDto;

public interface RefreshTokenService {
  RefreshTokenDto createRefreshToken(String username);

  RefreshTokenDto findByToken(String token);

  RefreshTokenDto verifyRefreshToken(RefreshTokenDto token);
}
