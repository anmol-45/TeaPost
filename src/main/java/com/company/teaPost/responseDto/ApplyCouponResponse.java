package com.company.teaPost.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplyCouponResponse {

    private String couponId;
    private Double originalAmount;
    private Double discount;
    private Double finalAmount;
    private String message;
}
