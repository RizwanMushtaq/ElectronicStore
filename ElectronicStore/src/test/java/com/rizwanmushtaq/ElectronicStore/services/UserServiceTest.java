package com.rizwanmushtaq.ElectronicStore.services;

import com.rizwanmushtaq.ElectronicStore.dtos.UserDto;
import com.rizwanmushtaq.ElectronicStore.entities.Role;
import com.rizwanmushtaq.ElectronicStore.entities.User;
import com.rizwanmushtaq.ElectronicStore.exceptions.ResourceNotFoundException;
import com.rizwanmushtaq.ElectronicStore.helper.Helper;
import com.rizwanmushtaq.ElectronicStore.repositories.RoleRepository;
import com.rizwanmushtaq.ElectronicStore.repositories.UserRepository;
import com.rizwanmushtaq.ElectronicStore.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  @InjectMocks
  private UserServiceImpl userService;
  private MockedStatic<Helper> mockedHelper;
  @Mock
  private RoleRepository roleRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private ModelMapper modelMapper;
  private UserDto userDto;
  private User userEntity;
  private User savedUser;
  private Role role;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockedHelper = mockStatic(Helper.class);
    userDto = new UserDto();
    userDto.setPassword("plainPassword");
    userEntity = new User();
    savedUser = new User();
    savedUser.setId("1234");
    savedUser.setPassword("encodedPassword");
    role = new Role();
    role.setName("ROLE_NORMAL");
  }

  @AfterEach
  void tearDown() {
    mockedHelper.close(); // release it to avoid leaks between tests
  }

  @Test
  void testCreateUser_Success() {
    mockedHelper.when(Helper::getUUID).thenReturn("uuid-1234");
    when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
    when(modelMapper.map(userDto, User.class)).thenReturn(userEntity);
    when(roleRepository.findByName("ROLE_NORMAL")).thenReturn(Optional.of(role));
    when(userRepository.save(userEntity)).thenReturn(savedUser);
    when(modelMapper.map(savedUser, UserDto.class)).thenReturn(userDto);
    UserDto result = userService.createUser(userDto);
    System.out.println(result);
    assertNotNull(result);
    assertEquals("uuid-1234", result.getId());
    assertEquals("encodedPassword", result.getPassword());
    verify(passwordEncoder).encode("plainPassword");
    verify(roleRepository).findByName("ROLE_NORMAL");
    verify(userRepository).save(userEntity);
    mockedHelper.verify(Helper::getUUID);
  }

  @Test
  void testCreateUser_RoleNotFound() {
    mockedHelper.when(Helper::getUUID).thenReturn("uuid-1234");
    when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
    when(modelMapper.map(userDto, User.class)).thenReturn(userEntity);
    when(roleRepository.findByName("ROLE_NORMAL")).thenReturn(Optional.empty());
    assertThrowsExactly(ResourceNotFoundException.class, () -> userService.createUser(userDto));
    verify(roleRepository).findByName("ROLE_NORMAL");
  }
}
