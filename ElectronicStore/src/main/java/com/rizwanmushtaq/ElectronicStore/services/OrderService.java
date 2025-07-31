package com.rizwanmushtaq.ElectronicStore.services;

import com.rizwanmushtaq.ElectronicStore.dtos.OrderDto;
import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;

import java.util.List;

public interface OrderService {
  OrderDto createOrder(OrderDto orderDto, String userId, String cartId);

  void removeOrder(String orderId);

  List<OrderDto> getOrdersOfUser(String userId);

  PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
}
