package com.rizwanmushtaq.ElectronicStore.services.implementations;

import com.rizwanmushtaq.ElectronicStore.dtos.CategoryDto;
import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.entities.Category;
import com.rizwanmushtaq.ElectronicStore.exceptions.ResourceNotFoundException;
import com.rizwanmushtaq.ElectronicStore.helper.Helper;
import com.rizwanmushtaq.ElectronicStore.repositories.CategoryRepository;
import com.rizwanmushtaq.ElectronicStore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CategoryServiceImpl implements CategoryService {
  Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private ModelMapper mapper;
  @Value("${category.image.path}")
  private String imagePath;

  @Override
  public CategoryDto create(CategoryDto categoryDto) {
    String id = Helper.getUUID();
    categoryDto.setId(id);
    Category category = mapper.map(categoryDto, Category.class);
    Category savedCategory = categoryRepository.save(category);
    return mapper.map(savedCategory, CategoryDto.class);
  }

  @Override
  public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
    Sort sort = Helper.getSortObject(sortDir, sortBy);
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
    Page<Category> page = categoryRepository.findAll(pageable);
    return Helper.getPageableResponse(page, CategoryDto.class);
  }

  @Override
  public CategoryDto getById(String id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    return mapper.map(category, CategoryDto.class);
  }

  @Override
  public CategoryDto getByTitle(String title) {
    Category category = categoryRepository.findByTitle(title)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found with title: " + title));
    return mapper.map(category, CategoryDto.class);
  }

  @Override
  public CategoryDto updateById(String id, CategoryDto categoryDto) {
    Category existingCategory =
        categoryRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Category not found with id:" + id)
        );
    existingCategory.setTitle(categoryDto.getTitle());
    existingCategory.setDescription(categoryDto.getDescription());
    existingCategory.setCoverImage(categoryDto.getCoverImage());
    Category updatedCategory = categoryRepository.save(existingCategory);
    return mapper.map(updatedCategory, CategoryDto.class);
  }

  @Override
  public void deleteById(String id) {
    Category existingCategory =
        categoryRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Category not found with id:" + id)
        );
    if (existingCategory.getCoverImage() != null) {
      String fullImagePath = imagePath + existingCategory.getCoverImage();
      try {
        Path path = Paths.get(fullImagePath);
        Files.delete(path);
        logger.info("Image deleted successfully: {}", fullImagePath);
      } catch (IOException exception) {
        logger.error("Error deleting image: {}", fullImagePath);
        exception.printStackTrace();
      }
    }
    categoryRepository.delete(existingCategory);
  }
}
