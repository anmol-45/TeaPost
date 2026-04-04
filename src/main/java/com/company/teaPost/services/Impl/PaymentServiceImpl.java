package com.company.teaPost.services.Impl;

import com.company.teaPost.appConstants.OrderStatus;
import com.company.teaPost.requestDto.PaymentCallbackRequest;
import com.company.teaPost.responseDto.PaymentCallbackResponse;
import com.company.teaPost.requestDto.PaymentRequest;
import com.company.teaPost.responseDto.PaymentResponse;
import com.company.teaPost.entities.Order;
import com.company.teaPost.entities.Payment;
import com.company.teaPost.repositories.OrderRepository;
import com.company.teaPost.repositories.PaymentRepository;
import com.company.teaPost.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponse initiatePayment(PaymentRequest request) {

        log.info("Initiating payment for orderId={}", request.getOrderId());

        // Fetch Order
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> {
                    log.error("Order not found orderId={}", request.getOrderId());
                    return new RuntimeException("Order not found");
                });

        // Validate
        if (!OrderStatus.CREATED.equals(order.getStatus())) {
            log.error("Payment not allowed for orderId={}, status={}",
                    order.getOrderId(), order.getStatus());
            throw new RuntimeException("Invalid order state for payment");
        }

        // Create Payment
        Payment payment = Payment.builder()
                .orderId(order.getOrderId())
                .amount(order.getTotalAmount())
                .paymentMode(request.getPaymentMode())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);


        return PaymentResponse.builder()
                .paymentId(savedPayment.getPaymentId())
                .status(savedPayment.getStatus())
                .amount(savedPayment.getAmount())
                .build();
    }

    @Override
    public PaymentCallbackResponse handlePaymentCallback(PaymentCallbackRequest request) {

        log.info("Received payment callback for orderId={}, status={}",
                request.getOrderId(), request.getStatus());

        // Fetch Order
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> {
                    log.error("Order not found for orderId={}", request.getOrderId());
                    return new RuntimeException("Order not found");
                });

        // Fetch Payment
        Payment payment = paymentRepository.findByOrderId(order.getOrderId())
                .orElseThrow(() -> {
                    log.error("Payment not found for orderId={}", order.getOrderId());
                    return new RuntimeException("Payment not found");
                });

        // Update Payment Status
        payment.setStatus(request.getStatus());
        paymentRepository.save(payment);

        log.info("Payment status updated for orderId={}, status={}",
                order.getOrderId(), request.getStatus());

        // Update Order Based on Payment
        if ("SUCCESS".equalsIgnoreCase(request.getStatus())) {
            order.setStatus(OrderStatus.COMPLETED);
        } else {
            order.setStatus(OrderStatus.CANCELLED);
        }

        orderRepository.save(order);

        log.info("Order status updated for orderId={}, newStatus={}",
                order.getOrderId(), order.getStatus());

        return PaymentCallbackResponse.builder()
                .message("Payment callback processed successfully")
                .build();
    }

}

