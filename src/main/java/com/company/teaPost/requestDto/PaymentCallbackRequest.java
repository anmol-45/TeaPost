package com.company.teaPost.requestDto;

import lombok.Data;

@Data
public class PaymentCallbackRequest {

    private Long orderId;
    private String status; // SUCCESS / FAILED
}
