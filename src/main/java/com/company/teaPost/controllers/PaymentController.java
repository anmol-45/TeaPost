package com.company.teaPost.controllers;

import com.company.teaPost.requestDto.PaymentCallbackRequest;
import com.company.teaPost.responseDto.PaymentCallbackResponse;
import com.company.teaPost.requestDto.PaymentRequest;
import com.company.teaPost.responseDto.PaymentResponse;
import com.company.teaPost.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> initiatePayment(@RequestBody PaymentRequest request) {

        log.info("API request received to initiate payment for orderId={}", request.getOrderId());

        PaymentResponse response = paymentService.initiatePayment(request);

        log.info("Payment processed successfully for orderId={}", request.getOrderId());

        return ResponseEntity.ok(response);
    }
    @PostMapping("/callback")
    public ResponseEntity<PaymentCallbackResponse> handleCallback(
            @RequestBody PaymentCallbackRequest request) {

        log.info("API request received for payment callback orderId={}", request.getOrderId());

        PaymentCallbackResponse response =
                paymentService.handlePaymentCallback(request);

        return ResponseEntity.ok(response);
    }



}
