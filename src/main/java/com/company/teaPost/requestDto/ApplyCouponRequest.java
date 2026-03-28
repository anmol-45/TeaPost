package com.company.teaPost.requestDto;

import lombok.Data;

@Data
public class ApplyCouponRequest {

    private Long orderId;
    private String couponCode;
}

