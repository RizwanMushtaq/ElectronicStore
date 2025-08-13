package com.rizwanmushtaq.ElectronicStore.services.implementations;

import com.rizwanmushtaq.ElectronicStore.dtos.RefreshTokenDto;
import com.rizwanmushtaq.ElectronicStore.entities.RefreshToken;
import com.rizwanmushtaq.ElectronicStore.entities.User;
import com.rizwanmushtaq.ElectronicStore.exceptions.ResourceNotFoundException;
import com.rizwanmushtaq.ElectronicStore.repositories.RefreshTokenRepository;
import com.rizwanmushtaq.ElectronicStore.repositories.UserRepository;
import com.rizwanmushtaq.ElectronicStore.services.RefreshTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RefreshTokenRepository refreshTokenRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public RefreshTokenDto createRefreshToken(String username) {
    User user =
        userRepository.findByUsername(username).orElseThrow(
            () -> new ResourceNotFoundException("user not found with id: " + username)
        );
    RefreshToken existingRefreshToken =
        refreshTokenRepository.findByUser(user).orElse(null);
    if (existingRefreshToken != null) {
      existingRefreshToken.setToken(UUID.randomUUID().toString());
      existingRefreshToken.setExpiryDate(Instant.now().plusSeconds(5 * 24 * 60 * 60));
      RefreshToken updatedRefreshToken =
          refreshTokenRepository.save(existingRefreshToken);
      return modelMapper.map(updatedRefreshToken, RefreshTokenDto.class);
    }
    RefreshToken refreshToken = RefreshToken
        .builder()
        .user(user)
        .token(UUID.randomUUID().toString())
        .expiryDate(Instant.now().plusSeconds(5 * 24 * 60 * 60))
        .build();
    RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
    return modelMapper.map(savedRefreshToken, RefreshTokenDto.class);
  }

  @Override
  public RefreshTokenDto findByToken(String token) {
    RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(
        () -> new ResourceNotFoundException("refresh token not found with token: " + token)
    );
    return modelMapper.map(refreshToken, RefreshTokenDto.class);
  }

  @Override
  public RefreshTokenDto verifyRefreshToken(RefreshTokenDto refreshTokenDto) {
    RefreshToken refreshToken = modelMapper.map(refreshTokenDto, RefreshToken.class);
    if (refreshTokenDto.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(refreshToken);
      throw new RuntimeException("Refresh Token Expired !!");
    }
    return refreshTokenDto;
  }
}
