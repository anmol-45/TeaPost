package com.company.teaPost.services;

import com.company.teaPost.requestDto.CreateOrderRequest;
import com.company.teaPost.responseDto.OrderDetailResponse;
import com.company.teaPost.responseDto.OrderResponse;
import org.springframework.web.bind.annotation.PathVariable;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    OrderDetailResponse getOrderById(String orderId);
    OrderResponse cancelOrder(@PathVariable String orderId);
}
