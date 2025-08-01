package com.rizwanmushtaq.ElectronicStore.services;

import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.dtos.ProductDto;

import java.util.List;

public interface ProductService {
  ProductDto create(ProductDto Product);

  PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

  ProductDto getById(String id);

  List<ProductDto> searchByTitle(String keyword);

  List<ProductDto> getLiveProducts(Boolean live);

  ProductDto updateById(String id, ProductDto Product);

  void deleteById(String id);

  ProductDto createProductWithCategory(String categoryId, ProductDto productDto);

  ProductDto updateProductWithCategory(String productId, String categoryId);

  PageableResponse<ProductDto> getProductsByCategory(
      String categoryId,
      int pageNumber,
      int pageSize,
      String sortBy,
      String sortDir
  );
}
