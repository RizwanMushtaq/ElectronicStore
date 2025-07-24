package com.rizwanmushtaq.ElectronicStore.repositories;

import com.rizwanmushtaq.ElectronicStore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
