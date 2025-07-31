package com.rizwanmushtaq.ElectronicStore.services.implementations;

import com.rizwanmushtaq.ElectronicStore.dtos.AddItemToCartRequest;
import com.rizwanmushtaq.ElectronicStore.dtos.CartDto;
import com.rizwanmushtaq.ElectronicStore.entities.Cart;
import com.rizwanmushtaq.ElectronicStore.entities.CartItem;
import com.rizwanmushtaq.ElectronicStore.entities.Product;
import com.rizwanmushtaq.ElectronicStore.entities.User;
import com.rizwanmushtaq.ElectronicStore.exceptions.ResourceNotFoundException;
import com.rizwanmushtaq.ElectronicStore.helper.Helper;
import com.rizwanmushtaq.ElectronicStore.repositories.CartItemRepository;
import com.rizwanmushtaq.ElectronicStore.repositories.CartRepository;
import com.rizwanmushtaq.ElectronicStore.repositories.ProductRepository;
import com.rizwanmushtaq.ElectronicStore.repositories.UserRepository;
import com.rizwanmushtaq.ElectronicStore.services.CartService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
  Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private CartItemRepository cartItemRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public CartDto addItemToCart(String userId, AddItemToCartRequest addItemToCartRequest) throws BadRequestException {
    int quantity = addItemToCartRequest.getQuantity();
    String productId = addItemToCartRequest.getProductId();
    if (quantity <= 0) {
      throw new BadRequestException("Quantity must be greater than zero.");
    }
    Product product = productRepository.findById(productId).orElseThrow(
        () -> new RuntimeException("Product not found with id: " + productId)
    );
    User user = userRepository.findById(userId).orElseThrow(
        () -> new RuntimeException("User not found with id: " + userId)
    );
    Cart cart = null;
    cart = cartRepository.findByUser(user).orElse(null);
    if (cart == null) {
      logger.info("Cart not found for user: " + userId + ", creating a new cart.");
      cart = new Cart();
      cart.setId(Helper.getUUID());
      cart.setCreatedAt(Helper.getCurrentDate());
      cart.setUser(user);
    }
    AtomicBoolean updatedExistingItem = new AtomicBoolean(false);
    List<CartItem> existingCartItems = cart.getCartItems();
    List<CartItem> updatedCartItems = existingCartItems.stream().map((item) -> {
      if (item.getProduct().getId() == productId) {
        item.setQuantity(item.getQuantity() + quantity);
        item.setTotalPrice(item.getTotalPrice() + (quantity * product.getPrice()));
        updatedExistingItem.set(true);
        logger.info("Updated existing cart item for product: {}", item);
        return item;
      } else {
        return item;
      }
    }).collect(Collectors.toList());
    if (updatedExistingItem.get()) {
      cart.getCartItems().clear();
      cart.getCartItems().addAll(updatedCartItems);
      logger.info("Cart item updated with updatedCartItems: {}", updatedCartItems);
    } else {
      CartItem cartItem = CartItem.builder()
          .quantity(quantity)
          .totalPrice(quantity * product.getPrice())
          .product(product)
          .cart(cart)
          .build();
      cart.getCartItems().add(cartItem);
    }
    Cart updatedCart = cartRepository.save(cart);
    return modelMapper.map(updatedCart, CartDto.class);
  }

  @Override
  public void removeItemFromCart(String userId, int cartItemId) {
    CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
        () -> new RuntimeException("Cart item not found with id: " + cartItemId)
    );
    cartItemRepository.delete(cartItem);
    logger.info("Removed cart item with id: {} for user: {}", cartItemId, userId);
  }

  @Override
  public void clearCart(String userId) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new RuntimeException("User not found with id: " + userId)
    );
    Cart cart = cartRepository.findByUser(user).orElseThrow(
        () -> new RuntimeException("Cart not found for user: " + userId)
    );
    cart.getCartItems().clear();
    cartRepository.save(cart);
    logger.info("Cleared cart for user: {}", userId);
  }

  @Override
  public CartDto getCartByUserId(String userId) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new RuntimeException("User not found with id: " + userId)
    );
    Cart cart = cartRepository.findByUser(user).orElseThrow(
        () -> new ResourceNotFoundException("Cart not found for user: " + userId)
    );
    return modelMapper.map(cart, CartDto.class);
  }
}
