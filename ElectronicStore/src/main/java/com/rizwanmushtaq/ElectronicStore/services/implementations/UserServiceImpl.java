package com.rizwanmushtaq.ElectronicStore.services.implementations;

import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.dtos.UserDto;
import com.rizwanmushtaq.ElectronicStore.entities.User;
import com.rizwanmushtaq.ElectronicStore.exceptions.ResourceNotFoundException;
import com.rizwanmushtaq.ElectronicStore.helper.Helper;
import com.rizwanmushtaq.ElectronicStore.repositories.UserRepository;
import com.rizwanmushtaq.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public UserDto createUser(UserDto userDto) {
    String userId = UUID.randomUUID().toString();
    userDto.setId(userId);
    User userEntity = modelMapper.map(userDto, User.class);
    User savedUser = userRepository.save(userEntity);
    return modelMapper.map(savedUser, UserDto.class);
  }

  @Override
  public UserDto getUserById(String id) {
    User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    return modelMapper.map(user, UserDto.class);
  }

  @Override
  public UserDto getUserByEmail(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    return modelMapper.map(user, UserDto.class);
  }

  @Override
  public List<UserDto> searchUsers(String keyword) {
    List<User> users = userRepository.findByNameContaining(keyword);
    return users.stream()
        .map(user -> modelMapper.map(user, UserDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
    Page<User> userPage = userRepository.findAll(pageable);
    return Helper.getPageableResponse(userPage, UserDto.class);
  }

  @Override
  public UserDto updateUser(String id, UserDto userdto) {
    User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    existingUser.setPassword(userdto.getPassword());
    existingUser.setUsername(userdto.getUsername());
    existingUser.setAbout(userdto.getAbout());
    existingUser.setGender(userdto.getGender());
    existingUser.setImageName(userdto.getImageName());
    existingUser.setName(userdto.getName());
    User savedUser = userRepository.save(existingUser);
    return modelMapper.map(savedUser, UserDto.class);
  }

  @Override
  public UserDto deleteUser(String id) {
    User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    userRepository.delete(user);
    return modelMapper.map(user, UserDto.class);
  }
}
