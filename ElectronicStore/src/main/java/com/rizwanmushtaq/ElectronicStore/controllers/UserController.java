package com.rizwanmushtaq.ElectronicStore.controllers;

import com.rizwanmushtaq.ElectronicStore.dtos.UserDto;
import com.rizwanmushtaq.ElectronicStore.services.UserService;
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
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
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
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,
                                            @RequestBody UserDto userDto) {
    return new ResponseEntity<>(userService.updateUser(userId, userDto),
        HttpStatus.OK);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<UserDto> deleteUser(@PathVariable("userId") String userId) {
    return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
  }
}
