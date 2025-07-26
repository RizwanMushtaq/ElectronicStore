package com.rizwanmushtaq.ElectronicStore.services;

import com.rizwanmushtaq.ElectronicStore.dtos.UserDto;

import java.util.List;

public interface UserService {
  UserDto createUser(UserDto user);

  UserDto getUserById(String id);

  UserDto getUserByEmail(String email);

  List<UserDto> searchUsers(String keyword);

  List<UserDto> getAllUsers(int pageNumber, int pageSize);

  UserDto updateUser(String id, UserDto user);

  UserDto deleteUser(String id);
}
