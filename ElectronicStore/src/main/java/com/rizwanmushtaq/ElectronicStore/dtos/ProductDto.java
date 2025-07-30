package com.rizwanmushtaq.ElectronicStore.dtos;

import com.rizwanmushtaq.ElectronicStore.entities.Category;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto {
  private String id;
  private String title;
  private String description;
  private int price;
  private int discountedPrice;
  private int quantity;
  private Date addedDate;
  private boolean live;
  private boolean stocked;
  private String productImageName;
  private Category category;
}
