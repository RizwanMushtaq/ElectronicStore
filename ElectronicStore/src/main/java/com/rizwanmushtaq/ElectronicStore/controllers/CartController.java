package com.rizwanmushtaq.ElectronicStore.controllers;

import com.rizwanmushtaq.ElectronicStore.config.AppConstants;
import com.rizwanmushtaq.ElectronicStore.dtos.AddItemToCartRequest;
import com.rizwanmushtaq.ElectronicStore.dtos.ApiResponseMessage;
import com.rizwanmushtaq.ElectronicStore.dtos.CartDto;
import com.rizwanmushtaq.ElectronicStore.services.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart Management", description = "Operations related to managing user carts in the E-Store")
@SecurityRequirement(name = "Bearer Authentication")
public class CartController {
  @Autowired
  private CartService cartService;

  @PreAuthorize("hasAnyRole('" + AppConstants.ROLE_NORMAL + "', '" + AppConstants.ROLE_ADMIN + "')")
  @PostMapping("/{userId}")
  public ResponseEntity<CartDto> addItemToCart(
      @PathVariable String userId,
      @RequestBody AddItemToCartRequest addItemToCartRequest
  ) throws BadRequestException {
    CartDto cartDto =
        cartService.addItemToCart(userId, addItemToCartRequest);
    return new ResponseEntity<>(cartDto, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('" + AppConstants.ROLE_NORMAL + "', '" + AppConstants.ROLE_ADMIN + "')")
  @DeleteMapping("/{userId}/items/{itemId}")
  public ResponseEntity<ApiResponseMessage> removeItemFromCart(
      @PathVariable String itemId,
      @PathVariable String userId
  ) {
    cartService.removeItemFromCart(userId, Integer.parseInt(itemId));
    ApiResponseMessage responseMessage = ApiResponseMessage
        .builder()
        .message("Item removed from cart successfully")
        .success(true)
        .status(HttpStatus.OK)
        .build();
    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('" + AppConstants.ROLE_NORMAL + "', '" + AppConstants.ROLE_ADMIN + "')")
  @DeleteMapping("/{userId}")
  public ResponseEntity<ApiResponseMessage> clearCart(
      @PathVariable String userId
  ) {
    cartService.clearCart(userId);
    ApiResponseMessage responseMessage = ApiResponseMessage
        .builder()
        .message("Cart cleared successfully")
        .success(true)
        .status(HttpStatus.OK)
        .build();
    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('" + AppConstants.ROLE_NORMAL + "', '" + AppConstants.ROLE_ADMIN + "')")
  @GetMapping("/{userId}")
  public ResponseEntity<CartDto> getCart(
      @PathVariable String userId
  ) {
    CartDto cartDto =
        cartService.getCartByUserId(userId);
    return new ResponseEntity<>(cartDto, HttpStatus.OK);
  }
}
