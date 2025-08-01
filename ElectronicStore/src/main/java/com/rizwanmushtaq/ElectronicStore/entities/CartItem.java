package com.rizwanmushtaq.ElectronicStore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "cart_items")
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  private Product product;
  private int quantity;
  private int totalPrice;
  @ManyToOne
  @JsonIgnore
  private Cart cart;
}
