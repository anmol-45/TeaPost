package com.company.teaPost.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private Long paymentId;
    private String status;
    private Double amount;
}

