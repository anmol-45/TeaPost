package com.company.teaPost.services.Impl;


import com.company.teaPost.appConstants.OrderStatus;
import com.company.teaPost.requestDto.CreateOrderRequest;
import com.company.teaPost.responseDto.OrderDetailResponse;
import com.company.teaPost.responseDto.OrderItemResponse;
import com.company.teaPost.responseDto.OrderResponse;
import com.company.teaPost.entities.Cart;
import com.company.teaPost.entities.CartItem;
import com.company.teaPost.entities.Order;
import com.company.teaPost.entities.OrderItem;
import com.company.teaPost.repositories.CartRepository;
import com.company.teaPost.repositories.OrderRepository;
import com.company.teaPost.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        log.info("Initiating order creation for userId={}", request.getUserId());

        // Fetch cart
        Cart cart = cartRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> {
                    log.error("Cart not found for userId={}", request.getUserId());
                    return new RuntimeException("Cart not found");
                });

        // Validate cart
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            log.error("Cart is empty for userId={}", request.getUserId());
            throw new RuntimeException("Cart is empty");
        }

        log.info("Cart fetched successfully for userId={}, itemsCount={}",
                request.getUserId(), cart.getItems().size());

        // Create Order
        Order order = Order.builder()
                .userId(request.getUserId())
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        // Convert CartItems → OrderItems
        for (CartItem cartItem : cart.getItems()) {

            log.debug("Processing cartItem productId={}, quantity={}",
                    cartItem.getProductId(), cartItem.getQuantity());

            OrderItem orderItem = OrderItem.builder()
                    .productId(cartItem.getProductId())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getPrice())
                    .order(order)
                    .build();

            totalAmount += cartItem.getPrice() * cartItem.getQuantity();
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        log.info("Order constructed for userId={}, totalAmount={}",
                request.getUserId(), totalAmount);

        // Save Order
        Order savedOrder = orderRepository.save(order);

        log.info("Order persisted successfully with orderId={}", savedOrder.getOrderId());

        // Clear Cart
        cart.getItems().clear();
        cartRepository.deleteById(cart.getCartId());

        log.info("Cart cleared for userId={}", request.getUserId());

        // Return Response
        return OrderResponse.builder()
                .orderId(savedOrder.getOrderId())
                .status(savedOrder.getStatus())
                .totalAmount(savedOrder.getTotalAmount())
                .build();
    }

    @Override
    public OrderDetailResponse getOrderById(String orderId) {

        log.info("Fetching order details for orderId={}", orderId);

        // Fetch Order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found for orderId={}", orderId);
                    return new RuntimeException("Order not found");
                });

        log.info("Order found for orderId={}, userId={}", orderId, order.getUserId());

        // Map OrderItems → DTO
        List<OrderItemResponse> itemResponses = order.getItems()
                .stream()
                .map(item -> OrderItemResponse.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .toList();

        log.debug("Mapped {} order items for orderId={}", itemResponses.size(), orderId);

        // Build Response
        return OrderDetailResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .status(order.getStatus().toString())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }

    @Override
    public OrderResponse cancelOrder(String orderId) {

        log.info("Initiating cancel request for orderId={}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found for orderId={}", orderId);
                    return new RuntimeException("Order not found");
                });

        log.info("Order fetched for cancellation orderId={}, currentStatus={}",
                orderId, order.getStatus());

        // Validate Status
        if (!OrderStatus.CREATED.equals(order.getStatus())) {

            log.error("Order cannot be cancelled. orderId={}, status={}",
                    orderId, order.getStatus());

            throw new RuntimeException("Order cannot be cancelled in current state");
        }

        // Update Status
        order.setStatus(OrderStatus.CANCELLED);

        //  Save
        Order updatedOrder = orderRepository.save(order);

        log.info("Order cancelled successfully for orderId={}", orderId);

        // Response
        return OrderResponse.builder()
                .orderId(updatedOrder.getOrderId())
                .status(updatedOrder.getStatus())
                .totalAmount(updatedOrder.getTotalAmount())
                .build();
    }


}
