package com.company.teaPost.requestDto;

import lombok.Data;

@Data
public class ApplyCouponRequest {

    private String orderId;
    private String couponCode;
}

