package com.rizwanmushtaq.ElectronicStore.dtos;

import com.rizwanmushtaq.ElectronicStore.entities.User;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartDto {
  private String id;
  private Date createdAt;
  private User user;
  private List<CartItemDto> cartItems = new ArrayList<>();
}
