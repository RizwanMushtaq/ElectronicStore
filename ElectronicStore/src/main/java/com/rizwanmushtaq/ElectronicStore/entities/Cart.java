package com.rizwanmushtaq.ElectronicStore.entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "carts")
public class Cart {
  @Id
  private String id;
  private Date createdAt;
  @OneToOne
  @JoinColumn(name = "user_id", unique = true)
  private User user;
  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<CartItem> cartItems = new ArrayList<>();
}
