package com.rizwanmushtaq.ElectronicStore.services;

import com.rizwanmushtaq.ElectronicStore.dtos.CategoryDto;
import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;

public interface CategoryService {
  CategoryDto create(CategoryDto category);

  PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

  CategoryDto getById(String id);

  CategoryDto getByTitle(String title);

  CategoryDto updateById(String id, CategoryDto category);

  void deleteById(String id);
}
