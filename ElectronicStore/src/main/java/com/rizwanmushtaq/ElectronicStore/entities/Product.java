package com.rizwanmushtaq.ElectronicStore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
  private String title;
  @Column(length = 1000)
  private String description;
  private int price;
  private int discountedPrice;
  private int quantity;
  private Date addedDate;
  private boolean live;
  private boolean stocked;
  private String productImageName;
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
