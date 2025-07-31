package com.rizwanmushtaq.ElectronicStore.services.implementations;

import com.rizwanmushtaq.ElectronicStore.dtos.OrderDto;
import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.entities.*;
import com.rizwanmushtaq.ElectronicStore.exceptions.BadApiRequest;
import com.rizwanmushtaq.ElectronicStore.exceptions.ResourceNotFoundException;
import com.rizwanmushtaq.ElectronicStore.helper.Helper;
import com.rizwanmushtaq.ElectronicStore.repositories.CartRepository;
import com.rizwanmushtaq.ElectronicStore.repositories.OrderRepository;
import com.rizwanmushtaq.ElectronicStore.repositories.UserRepository;
import com.rizwanmushtaq.ElectronicStore.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public OrderDto createOrder(OrderDto orderDto, String userId, String cartId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    Cart cart = cartRepository.findById(cartId)
        .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
    List<CartItem> cartItems = cart.getCartItems();
    if (cartItems.size() == 0) {
      throw new BadApiRequest("Cart is empty for user with id: " + userId);
    }
    Order order = Order.builder()
        .billingName(orderDto.getBillingName())
        .billingAddress(orderDto.getBillingAddress())
        .billingPhone(orderDto.getBillingPhone())
        .orderStatus(orderDto.getOrderStatus())
        .orderedDate(orderDto.getOrderedDate())
        .deliveredDate(null)
        .paymentStatus(orderDto.getPaymentStatus())
        .id(Helper.getUUID())
        .user(user)
        .build();
    AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
    List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
      orderAmount.set(orderAmount.get() + cartItem.getTotalPrice());
      return OrderItem
          .builder()
          .quantity(cartItem.getQuantity())
          .totalPrice(cartItem.getTotalPrice())
          .order(order)
          .build();
    }).collect(Collectors.toList());
    order.setOrderItems(orderItems);
    order.setOrderAmount(orderAmount.get());
    cart.getCartItems().clear();
    cartRepository.save(cart);
    Order savedOrder = orderRepository.save(order);
    return modelMapper.map(savedOrder, OrderDto.class);
  }

  @Override
  public void removeOrder(String orderId) {
  }

  @Override
  public List<OrderDto> getOrdersOfUser(String userId) {
    return List.of();
  }

  @Override
  public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
    return null;
  }
}
