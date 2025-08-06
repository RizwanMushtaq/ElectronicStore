package com.rizwanmushtaq.ElectronicStore.controllers;

import com.rizwanmushtaq.ElectronicStore.dtos.ApiResponseMessage;
import com.rizwanmushtaq.ElectronicStore.dtos.ImageResponse;
import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.dtos.UserDto;
import com.rizwanmushtaq.ElectronicStore.exceptions.ResourceNotFoundException;
import com.rizwanmushtaq.ElectronicStore.services.FileService;
import com.rizwanmushtaq.ElectronicStore.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  Logger logger = LoggerFactory.getLogger(UserController.class);
  @Autowired
  private UserService userService;
  @Autowired
  private FileService fileService;
  @Value("${user.profile.image.path}")
  private String imageUploadPath;

  @PostMapping
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
    logger.info("called create User");
    return new ResponseEntity<>(userService.createUser(userDto),
        HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
      @RequestParam(value = "pageNumber", defaultValue = "1", required =
          false) int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10",
          required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
  ) {
    logger.info("called getAllUsers");
    return new ResponseEntity<>(userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir),
        HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
    return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email
  ) {
    return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
  }

  @GetMapping("/search/{keyword}")
  public ResponseEntity<List<UserDto>> searchUsers(@PathVariable String keyword) {
    List<UserDto> users = userService.searchUsers(keyword);
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserDto> updateUser(@PathVariable String userId,
                                            @Valid @RequestBody UserDto userDto) {
    return new ResponseEntity<>(userService.updateUser(userId, userDto),
        HttpStatus.OK);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
    userService.deleteUser(userId);
    ApiResponseMessage responseMessage = ApiResponseMessage
        .builder()
        .message("User deleted successfully")
        .success(true)
        .status(HttpStatus.OK)
        .build();
    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
  }

  @PostMapping("/image/{userId}")
  public ResponseEntity<ImageResponse> uploadUserImage(
      @RequestParam("userImage") MultipartFile image,
      @PathVariable String userId
  ) {
    UserDto user = userService.getUserById(userId);
    String imageName = fileService.uploadImage(image, imageUploadPath);
    logger.info("Image name: {}", imageName);
    user.setImageName(imageName);
    logger.info("User: {}", user);
    userService.updateUser(userId, user);
    ImageResponse imageResponse = ImageResponse
        .builder()
        .imageName(imageName)
        .message("Image uploaded successfully")
        .success(true)
        .status(HttpStatus.CREATED)
        .build();
    return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
  }

  @GetMapping("/image/{userId}")
  public void serveUserImage(
      @PathVariable String userId,
      HttpServletResponse response
  ) throws IOException {
    UserDto user = userService.getUserById(userId);
    String imageName = user.getImageName();
    logger.info("Serving image: {}", imageName);
    if (imageName != null) {
      InputStream resource = fileService.getResource(imageUploadPath, imageName);
      response.setContentType(MediaType.IMAGE_JPEG_VALUE);
      StreamUtils.copy(resource, response.getOutputStream());
    } else {
      throw new ResourceNotFoundException("Image not found for user: " + userId);
    }
  }
}
