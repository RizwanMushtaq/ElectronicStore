package com.rizwanmushtaq.ElectronicStore.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "order_items")
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int quantity;
  private int totalPrice;
  @ManyToOne
  @ToString.Exclude
  private Product product;
  @ManyToOne
  @ToString.Exclude
  private Order order;
}
