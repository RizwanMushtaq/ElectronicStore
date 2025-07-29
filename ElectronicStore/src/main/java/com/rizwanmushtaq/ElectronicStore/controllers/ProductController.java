package com.rizwanmushtaq.ElectronicStore.controllers;

import com.rizwanmushtaq.ElectronicStore.dtos.ApiResponseMessage;
import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.dtos.ProductDto;
import com.rizwanmushtaq.ElectronicStore.services.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  Logger logger = LoggerFactory.getLogger(ProductController.class);
  @Autowired
  private ProductService productService;

  @PostMapping
  public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto productDto) {
    logger.info("Creating new category with: {}", productDto);
    ProductDto createdCategory = productService.create(productDto);
    return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<PageableResponse<ProductDto>> getAll(
      @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10",
          required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
  ) {
    return new ResponseEntity<>(productService.getAll(
        pageNumber,
        pageSize,
        sortBy,
        sortDir
    ), HttpStatus.OK);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductDto> getById(@PathVariable String productId) {
    return new ResponseEntity<>(productService.getById(productId), HttpStatus.OK);
  }

  @GetMapping("/search/{keyword}")
  public ResponseEntity<List<ProductDto>> searchByTitle(@PathVariable String keyword) {
    List<ProductDto> products = productService.searchByTitle(keyword);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping("/live/{live}")
  public ResponseEntity<List<ProductDto>> getLiveProducts(@PathVariable Boolean live) {
    List<ProductDto> products = productService.getLiveProducts(live);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ProductDto> updateById(@PathVariable String productId,
                                               @Valid @RequestBody ProductDto productDto) {
    logger.info("Updating product with ID: {} with data: {}", productId, productDto);
    ProductDto updatedProduct = productService.updateById(productId, productDto);
    return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<ApiResponseMessage> deleteById(@PathVariable String productId) {
    logger.info("Deleting product with ID: {}", productId);
    productService.deleteById(productId);
    ApiResponseMessage responseMessage = ApiResponseMessage
        .builder()
        .message("User deleted successfully")
        .success(true)
        .status(HttpStatus.OK)
        .build();
    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
  }
}
