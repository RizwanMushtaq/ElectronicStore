package com.rizwanmushtaq.ElectronicStore.services.implementations;

import com.rizwanmushtaq.ElectronicStore.dtos.UserDto;
import com.rizwanmushtaq.ElectronicStore.entities.User;
import com.rizwanmushtaq.ElectronicStore.repositories.UserRepository;
import com.rizwanmushtaq.ElectronicStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDto createUser(UserDto userDto) {
    String userId = UUID.randomUUID().toString();
    userDto.setId(userId);
    User userEntity = dtoToEntity(userDto);
    User savedUser = userRepository.save(userEntity);
    UserDto newuserDto = entityToDto(savedUser);
    return newuserDto;
  }

  @Override
  public UserDto getUserById(String id) {
    return null;
  }

  @Override
  public UserDto getUserByEmail(String email) {
    return null;
  }

  @Override
  public List<UserDto> getAllUsers() {
    return List.of();
  }

  @Override
  public UserDto updateUser(String id, UserDto user) {
    return null;
  }

  @Override
  public UserDto deleteUser(String id) {
    return null;
  }

  private UserDto entityToDto(User user) {
    UserDto userDto = UserDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .password(user.getPassword())
        .username(user.getUsername())
        .about(user.getAbout())
        .gender(user.getGender())
        .imageName(user.getImageName())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .build();
    return userDto;
  }

  private User dtoToEntity(UserDto userDto) {
    User user = User.builder()
        .id(userDto.getId())
        .email(userDto.getEmail())
        .password(userDto.getPassword())
        .username(userDto.getUsername())
        .about(userDto.getAbout())
        .gender(userDto.getGender())
        .imageName(userDto.getImageName())
        .firstName(userDto.getFirstName())
        .lastName(userDto.getLastName())
        .build();
    return user;
  }
}
