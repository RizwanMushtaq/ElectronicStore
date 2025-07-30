package com.rizwanmushtaq.ElectronicStore.services;

import com.rizwanmushtaq.ElectronicStore.dtos.AddItemToCartRequest;
import com.rizwanmushtaq.ElectronicStore.dtos.CartDto;
import org.apache.coyote.BadRequestException;

public interface CartService {
  /*
   * add item to cart
   * case1: cart for user does not exist, we will create a new cart
   * case2: cart for user exists, we will add the item to the existing cart
   */
  CartDto addItemToCart(String userId, AddItemToCartRequest addItemToCartRequest) throws BadRequestException;

  /*
   * remove item from cart
   */
  void removeItemFromCart(String userId, int cartItemId);

  /*
   * clear cart
   */
  void clearCart(String userId);

  CartDto getCartByUserId(String userId);
}
