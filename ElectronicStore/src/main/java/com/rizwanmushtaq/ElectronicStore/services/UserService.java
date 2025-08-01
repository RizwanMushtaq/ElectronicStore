package com.rizwanmushtaq.ElectronicStore.services;

import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.dtos.UserDto;

import java.util.List;

public interface UserService {
  UserDto createUser(UserDto user);

  UserDto getUserById(String id);

  UserDto getUserByEmail(String email);

  List<UserDto> searchUsers(String keyword);

  PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

  UserDto updateUser(String id, UserDto user);

  void deleteUser(String id);
}
