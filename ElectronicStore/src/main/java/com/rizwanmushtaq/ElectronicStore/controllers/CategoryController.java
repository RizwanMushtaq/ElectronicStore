package com.rizwanmushtaq.ElectronicStore.controllers;

import com.rizwanmushtaq.ElectronicStore.dtos.ApiResponseMessage;
import com.rizwanmushtaq.ElectronicStore.dtos.CategoryDto;
import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

  @PostMapping
  public ResponseEntity<CategoryDto> create(CategoryDto categoryDto) {
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

  @GetMapping("/{categoryTile}")
  public ResponseEntity<CategoryDto> getCategoryByTitle(@PathVariable String categoryTile) {
    return new ResponseEntity<>(categoryService.getByTitle(categoryTile), HttpStatus.OK);
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryDto> updateCategoryById(
      @PathVariable String categoryId,
      @RequestBody CategoryDto categoryDto
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
}
