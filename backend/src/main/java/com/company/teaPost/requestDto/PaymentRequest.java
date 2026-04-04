package com.company.teaPost.requestDto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private String paymentMode;
}

