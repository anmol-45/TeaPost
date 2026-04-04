package com.company.teaPost.services;

import com.company.teaPost.requestDto.PaymentCallbackRequest;
import com.company.teaPost.responseDto.PaymentCallbackResponse;
import com.company.teaPost.requestDto.PaymentRequest;
import com.company.teaPost.responseDto.PaymentResponse;

public interface PaymentService {
    PaymentResponse initiatePayment(PaymentRequest request);

    PaymentCallbackResponse handlePaymentCallback(PaymentCallbackRequest request);

}