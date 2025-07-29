package com.rizwanmushtaq.ElectronicStore.repositories;

import com.rizwanmushtaq.ElectronicStore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
  List<Product> findByTitleContaining(String title);

  List<Product> findByLive(Boolean live);
}
