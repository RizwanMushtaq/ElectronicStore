package com.rizwanmushtaq.ElectronicStore.dtos;

import com.rizwanmushtaq.ElectronicStore.entities.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderItemDto {
  private int id;
  private int quantity;
  private int totalPrice;
  private Product product;
}
