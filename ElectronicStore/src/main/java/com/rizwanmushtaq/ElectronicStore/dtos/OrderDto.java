package com.rizwanmushtaq.ElectronicStore.dtos;

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
public class OrderDto {
  private String id;
  private String orderStatus = "PENDING";
  private String paymentStatus = "NOT_PAID";
  private int orderAmount;
  private String billingName;
  private String billingAddress;
  private String billingPhone;
  private Date orderedDate = new Date();
  private Date deliveredDate;
  private UserDto user;
  private List<OrderItemDto> orderItems = new ArrayList<>();
}
