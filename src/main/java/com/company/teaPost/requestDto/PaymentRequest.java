package com.company.teaPost.requestDto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long orderId;
    private String paymentMode;
}

