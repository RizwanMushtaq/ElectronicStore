package com.rizwanmushtaq.ElectronicStore.services;

import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.dtos.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
  ProductDto create(ProductDto Product);

  PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

  ProductDto getById(String id);

  List<ProductDto> searchByTitle(String keyword);

  List<ProductDto> getLiveProducts(Boolean live);

  ProductDto updateById(String id, ProductDto Product);

  void deleteById(String id);
}
