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
@Builder
@ToString
@Entity
@Table(name = "orders")
public class Order {
  @Id
  private String id;
  private String orderStatus;
  private String paymentStatus;
  private int orderAmount;
  private String billingName;
  @Column(length = 1000)
  private String billingAddress;
  private String billingPhone;
  private Date orderedDate;
  private Date deliveredDate;
  @ManyToOne
  private User user;
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch =
      FetchType.EAGER, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();
}
