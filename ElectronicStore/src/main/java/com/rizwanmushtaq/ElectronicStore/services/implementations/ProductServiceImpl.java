package com.rizwanmushtaq.ElectronicStore.services.implementations;

import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.dtos.ProductDto;
import com.rizwanmushtaq.ElectronicStore.entities.Product;
import com.rizwanmushtaq.ElectronicStore.exceptions.ResourceNotFoundException;
import com.rizwanmushtaq.ElectronicStore.helper.Helper;
import com.rizwanmushtaq.ElectronicStore.repositories.ProductRepository;
import com.rizwanmushtaq.ElectronicStore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public ProductDto create(ProductDto ProductDto) {
    Product product = modelMapper.map(ProductDto, Product.class);
    Product savedProduct = productRepository.save(product);
    return modelMapper.map(savedProduct, ProductDto.class);
  }

  @Override
  public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
    Sort sort = Helper.getSortObject(sortDir, sortBy);
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
    Page<Product> page = productRepository.findAll(pageable);
    return Helper.getPageableResponse(page, ProductDto.class);
  }

  @Override
  public ProductDto getById(String id) {
    Product product = productRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Product with given id not found: " + id)
    );
    return modelMapper.map(product, ProductDto.class);
  }

  @Override
  public List<ProductDto> searchByTitle(String keyword) {
    List<Product> products = productRepository.findByTitleContaining(keyword);
    return products
        .stream()
        .map(product -> modelMapper.map(product, ProductDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public List<ProductDto> getLiveProducts(Boolean live) {
    List<Product> products = productRepository.findByLiveTrue(live);
    return products
        .stream()
        .map(product -> modelMapper.map(product, ProductDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public ProductDto updateById(String id, ProductDto productDto) {
    Product existingProduct = productRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Product with given id not found: " + id)
    );
    existingProduct.setTitle(productDto.getTitle());
    existingProduct.setDescription(productDto.getDescription());
    existingProduct.setPrice(productDto.getPrice());
    existingProduct.setPrice(productDto.getPrice());
    existingProduct.setDiscountedPrice(productDto.getDiscountedPrice());
    existingProduct.setQuantity(productDto.getQuantity());
    existingProduct.setAddedDate(productDto.getAddedDate());
    existingProduct.setLive(productDto.isLive());
    existingProduct.setStocked(productDto.isStocked());
    Product updatedProduct = productRepository.save(existingProduct);
    return modelMapper.map(updatedProduct, ProductDto.class);
  }

  @Override
  public void deleteById(String id) {
    Product product = productRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Product with given id not found: " + id)
    );
    productRepository.delete(product);
  }
}
