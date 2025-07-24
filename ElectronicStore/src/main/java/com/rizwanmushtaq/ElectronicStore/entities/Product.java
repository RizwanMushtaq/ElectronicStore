package com.rizwanmushtaq.ElectronicStore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "products")
public class Product {
  @Id
  private String id;
  private String name;
  private String description;
  private double price;
  private String imageName;
  private String category;
  private boolean active;
}
