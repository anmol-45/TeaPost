package com.company.teaPost.controllers;

import com.company.teaPost.requestDto.CreateOrderRequest;
import com.company.teaPost.responseDto.OrderDetailResponse;
import com.company.teaPost.responseDto.OrderResponse;
import com.company.teaPost.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {

        log.info("Received request to create order for userId={}", request.getUserId());

        OrderResponse response = orderService.createOrder(request);

        log.info("Order created successfully for userId={}, orderId={}",
                request.getUserId(), response.getOrderId());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrderById(@PathVariable Long orderId) {

        log.info("API request received to fetch order orderId={}", orderId);

        OrderDetailResponse response = orderService.getOrderById(orderId);

        log.info("Order fetched successfully for orderId={}", orderId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {

        log.info("API request received to cancel orderId={}", orderId);

        OrderResponse response = orderService.cancelOrder(orderId);

        log.info("Order cancelled successfully for orderId={}", orderId);

        return ResponseEntity.ok(response);
    }


}

