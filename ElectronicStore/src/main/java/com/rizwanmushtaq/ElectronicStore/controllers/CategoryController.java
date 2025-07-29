package com.rizwanmushtaq.ElectronicStore.controllers;

import com.rizwanmushtaq.ElectronicStore.dtos.ApiResponseMessage;
import com.rizwanmushtaq.ElectronicStore.dtos.CategoryDto;
import com.rizwanmushtaq.ElectronicStore.dtos.ImageResponse;
import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.exceptions.ResourceNotFoundException;
import com.rizwanmushtaq.ElectronicStore.services.CategoryService;
import com.rizwanmushtaq.ElectronicStore.services.FileService;
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

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
  Logger logger = LoggerFactory.getLogger(CategoryController.class);
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private FileService fileService;
  @Value("${category.image.path}")
  private String imageUploadPath;

  @PostMapping
  public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto) {
    logger.info("Creating new category with: {}", categoryDto);
    CategoryDto createdCategory = categoryService.create(categoryDto);
    return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<PageableResponse<CategoryDto>> getAllUsers(
      @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10",
          required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
  ) {
    return new ResponseEntity<>(categoryService.getAll(
        pageNumber,
        pageSize,
        sortBy,
        sortDir
    ), HttpStatus.OK);
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId) {
    return new ResponseEntity<>(categoryService.getById(categoryId), HttpStatus.OK);
  }

  @GetMapping("/title/{categoryTile}")
  public ResponseEntity<CategoryDto> getCategoryByTitle(@PathVariable String categoryTile) {
    return new ResponseEntity<>(categoryService.getByTitle(categoryTile), HttpStatus.OK);
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryDto> updateCategoryById(
      @PathVariable String categoryId,
      @Valid @RequestBody CategoryDto categoryDto
  ) {
    CategoryDto updatedCategory = categoryService.updateById(categoryId, categoryDto);
    return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<ApiResponseMessage> deleteCategoryById(@PathVariable String categoryId) {
    categoryService.deleteById(categoryId);
    ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
        .message("Category deleted successfully")
        .success(true)
        .status(HttpStatus.OK)
        .build();
    return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
  }

  @PostMapping("/image/{categoryId}")
  public ResponseEntity<ImageResponse> uploadImage(
      @RequestParam("categoryImage") MultipartFile image,
      @PathVariable String categoryId
  ) {
    CategoryDto category = categoryService.getById(categoryId);
    String imageName = fileService.uploadImage(image, imageUploadPath);
    logger.info("Image name: {}", imageName);
    category.setCoverImage(imageName);
    logger.info("Category: {}", category);
    categoryService.updateById(categoryId, category);
    ImageResponse imageResponse = ImageResponse
        .builder()
        .imageName(imageName)
        .message("Image uploaded successfully")
        .success(true)
        .status(HttpStatus.CREATED)
        .build();
    return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
  }

  @GetMapping("/image/{categoryId}")
  public void serveImage(
      @PathVariable String categoryId,
      HttpServletResponse response
  ) throws IOException {
    CategoryDto category = categoryService.getById(categoryId);
    String imageName = category.getCoverImage();
    logger.info("Serving image: {}", imageName);
    if (imageName != null) {
      InputStream resource = fileService.getResource(imageUploadPath, imageName);
      response.setContentType(MediaType.IMAGE_JPEG_VALUE);
      StreamUtils.copy(resource, response.getOutputStream());
    } else {
      throw new ResourceNotFoundException("Image not found for user: " + categoryId);
    }
  }
}
