package com.company.teaPost.requestDto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCouponRequest {

    private String code;
    private Double discountPercentage;
    private Double maxDiscount;
    private Double minOrderAmount;
    private LocalDateTime expiryDate;
    private boolean active;
    private int usageLimit;
}
