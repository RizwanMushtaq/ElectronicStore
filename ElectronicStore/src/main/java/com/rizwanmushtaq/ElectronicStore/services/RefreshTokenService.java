package com.rizwanmushtaq.ElectronicStore.services;

import com.rizwanmushtaq.ElectronicStore.dtos.RefreshTokenDto;
import com.rizwanmushtaq.ElectronicStore.dtos.UserDto;

public interface RefreshTokenService {
  RefreshTokenDto createRefreshToken(String username);

  RefreshTokenDto findByToken(String token);

  RefreshTokenDto verifyRefreshToken(RefreshTokenDto token);

  UserDto getUser(RefreshTokenDto refreshTokenDto);
}
