package com.rizwanmushtaq.ElectronicStore.controllers;

import com.rizwanmushtaq.ElectronicStore.dtos.ApiResponseMessage;
import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.dtos.UserDto;
import com.rizwanmushtaq.ElectronicStore.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
    return new ResponseEntity<>(userService.createUser(userDto),
        HttpStatus.CREATED);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId) {
    return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email
  ) {
    return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
  }

  @GetMapping("/search/{keyword}")
  public ResponseEntity<List<UserDto>> searchUsers(@PathVariable("keyword") String keyword) {
    List<UserDto> users = userService.searchUsers(keyword);
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
      @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10",
          required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
  ) {
    return new ResponseEntity<>(userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir),
        HttpStatus.OK);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,
                                            @Valid @RequestBody UserDto userDto) {
    return new ResponseEntity<>(userService.updateUser(userId, userDto),
        HttpStatus.OK);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) {
    userService.deleteUser(userId);
    ApiResponseMessage responseMessage = ApiResponseMessage
        .builder()
        .message("User deleted successfully")
        .success(true)
        .status(HttpStatus.OK)
        .build();
    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
  }
}
