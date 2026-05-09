package com.company.teaPost.requestDto;

import lombok.Data;

@Data
public class PaymentCallbackRequest {

    private String orderId;
    private String status;
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
}
