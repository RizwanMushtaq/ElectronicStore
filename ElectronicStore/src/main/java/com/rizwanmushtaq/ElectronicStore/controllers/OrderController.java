package com.rizwanmushtaq.ElectronicStore.controllers;

import com.rizwanmushtaq.ElectronicStore.dtos.ApiResponseMessage;
import com.rizwanmushtaq.ElectronicStore.dtos.CreateOrderRequest;
import com.rizwanmushtaq.ElectronicStore.dtos.OrderDto;
import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import com.rizwanmushtaq.ElectronicStore.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  Logger logger = LoggerFactory.getLogger(OrderController.class);
  @Autowired
  private OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderDto> createOrder(
      @RequestBody CreateOrderRequest createOrderRequest
  ) {
    OrderDto orderDto = OrderDto.builder()
        .billingName(createOrderRequest.getBillingName())
        .billingAddress(createOrderRequest.getBillingAddress())
        .billingPhone(createOrderRequest.getBillingPhone())
        .build();
    String userId = createOrderRequest.getUserId();
    String cartId = createOrderRequest.getCartId();
    OrderDto createdOrderDto = orderService.createOrder(orderDto, userId,
        cartId);
    return new ResponseEntity<>(createdOrderDto, HttpStatus.CREATED);
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<ApiResponseMessage> removeOrder(
      @PathVariable String orderId
  ) {
    orderService.removeOrder(orderId);
    ApiResponseMessage responseMessage = ApiResponseMessage
        .builder()
        .message("Order removed successfully")
        .success(true)
        .status(HttpStatus.OK)
        .build();
    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<List<OrderDto>> getOrdersOfUser(
      @PathVariable String userId
  ) {
    return new ResponseEntity<>(orderService.getOrdersOfUser(userId), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<PageableResponse<OrderDto>> getOrders(
      @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
  ) {
    logger.info("Fetching orders with pageNumber: {}, pageSize: {}, sortBy: {}, sortDir: {}", pageNumber, pageSize, sortBy, sortDir);
    PageableResponse<OrderDto> pageableResponse = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
    return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
  }
}
