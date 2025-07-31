package com.rizwanmushtaq.ElectronicStore.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {
  @NotBlank(message = "User ID cannot be blank")
  private String userId;
  @NotBlank(message = "Cart ID cannot be blank")
  private String cartId;
  @NotBlank(message = "Billing name cannot be blank")
  private String billingName;
  @NotBlank(message = "Billing address cannot be blank")
  private String billingAddress;
  @NotBlank(message = "Billing phone cannot be blank")
  private String billingPhone;
}
