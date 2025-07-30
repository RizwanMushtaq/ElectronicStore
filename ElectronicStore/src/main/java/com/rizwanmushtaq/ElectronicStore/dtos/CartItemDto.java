package com.rizwanmushtaq.ElectronicStore.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartItemDto {
  private int id;
  private ProductDto product;
  private int quantity;
  private int totalPrice;
}
