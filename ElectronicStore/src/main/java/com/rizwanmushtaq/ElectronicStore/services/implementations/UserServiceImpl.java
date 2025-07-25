package com.rizwanmushtaq.ElectronicStore.services.implementations;

import com.rizwanmushtaq.ElectronicStore.dtos.UserDto;
import com.rizwanmushtaq.ElectronicStore.entities.User;
import com.rizwanmushtaq.ElectronicStore.repositories.UserRepository;
import com.rizwanmushtaq.ElectronicStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    return entityToDto(savedUser);
  }

  @Override
  public UserDto getUserById(String id) {
    User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    return entityToDto(user);
  }

  @Override
  public UserDto getUserByEmail(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    return entityToDto(user);
  }

  @Override
  public List<UserDto> searchUsers(String keyword) {
    List<User> users = userRepository.findByNameContaining(keyword);
    return users.stream()
        .map(user -> entityToDto(user))
        .collect(Collectors.toList());
  }

  @Override
  public List<UserDto> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(user -> entityToDto(user))
        .collect(Collectors.toList());
  }

  @Override
  public UserDto updateUser(String id, UserDto userdto) {
    User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    existingUser.setPassword(userdto.getPassword());
    existingUser.setUsername(userdto.getUsername());
    existingUser.setAbout(userdto.getAbout());
    existingUser.setGender(userdto.getGender());
    existingUser.setImageName(userdto.getImageName());
    existingUser.setName(userdto.getName());
    return entityToDto(existingUser);
  }

  @Override
  public UserDto deleteUser(String id) {
    User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    userRepository.delete(user);
    return entityToDto(user);
  }

  private UserDto entityToDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .password(user.getPassword())
        .username(user.getUsername())
        .about(user.getAbout())
        .gender(user.getGender())
        .imageName(user.getImageName())
        .name(user.getName())
        .build();
  }

  private User dtoToEntity(UserDto userDto) {
    return User.builder()
        .id(userDto.getId())
        .email(userDto.getEmail())
        .password(userDto.getPassword())
        .username(userDto.getUsername())
        .about(userDto.getAbout())
        .gender(userDto.getGender())
        .imageName(userDto.getImageName())
        .name(userDto.getName())
        .build();
  }
}
