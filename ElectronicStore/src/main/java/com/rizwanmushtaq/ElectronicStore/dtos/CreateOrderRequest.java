package com.rizwanmushtaq.ElectronicStore.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {
  private String userId;
  private String cartId;
  private String billingName;
  private String billingAddress;
  private String billingPhone;
}
