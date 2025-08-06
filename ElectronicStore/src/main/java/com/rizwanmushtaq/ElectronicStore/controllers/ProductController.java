package com.rizwanmushtaq.ElectronicStore.controllers;

import com.rizwanmushtaq.ElectronicStore.dtos.ApiResponseMessage;
import com.rizwanmushtaq.ElectronicStore.dtos.ImageResponse;
import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.dtos.ProductDto;
import com.rizwanmushtaq.ElectronicStore.exceptions.ResourceNotFoundException;
import com.rizwanmushtaq.ElectronicStore.services.FileService;
import com.rizwanmushtaq.ElectronicStore.services.ProductService;
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
@RequestMapping("/api/products")
public class ProductController {
  Logger logger = LoggerFactory.getLogger(ProductController.class);
  @Autowired
  private ProductService productService;
  @Autowired
  private FileService fileService;
  @Value("${product.image.path}")
  private String imageUploadPath;

  @PostMapping
  public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto productDto) {
    logger.info("Creating new product with: {}", productDto);
    ProductDto createdProduct = productService.create(productDto);
    return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<PageableResponse<ProductDto>> getAll(
      @RequestParam(value = "pageNumber", defaultValue = "1", required =
          false) int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "title",
          required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
  ) {
    logger.info("called getAll Products");
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
        .message("Product deleted successfully")
        .success(true)
        .status(HttpStatus.OK)
        .build();
    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
  }

  @PostMapping("/image/{productId}")
  public ResponseEntity<ImageResponse> uploadImage(
      @RequestParam("productImage") MultipartFile image,
      @PathVariable String productId
  ) {
    ProductDto product = productService.getById(productId);
    String imageName = fileService.uploadImage(image, imageUploadPath);
    logger.info("Image name: {}", imageName);
    product.setProductImageName(imageName);
    logger.info("Product: {}", product);
    ProductDto updatedProduct = productService.updateById(productId, product);
    logger.info("updatedProduct: {}", updatedProduct);
    ImageResponse imageResponse = ImageResponse
        .builder()
        .imageName(imageName)
        .message("Image uploaded successfully")
        .success(true)
        .status(HttpStatus.CREATED)
        .build();
    return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
  }

  @GetMapping("/image/{productId}")
  public void serveImage(
      @PathVariable String productId,
      HttpServletResponse response
  ) throws IOException {
    ProductDto product = productService.getById(productId);
    String imageName = product.getProductImageName();
    logger.info("Serving image: {}", imageName);
    if (imageName != null) {
      InputStream resource = fileService.getResource(imageUploadPath, imageName);
      response.setContentType(MediaType.IMAGE_JPEG_VALUE);
      StreamUtils.copy(resource, response.getOutputStream());
    } else {
      throw new ResourceNotFoundException("Image not found for product: " + productId);
    }
  }
}
