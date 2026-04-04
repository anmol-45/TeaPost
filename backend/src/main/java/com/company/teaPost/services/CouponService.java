package com.company.teaPost.services;

import com.company.teaPost.requestDto.ApplyCouponRequest;
import com.company.teaPost.responseDto.ApplyCouponResponse;
import com.company.teaPost.requestDto.CreateCouponRequest;
import com.company.teaPost.responseDto.CreateCouponResponse;

public interface CouponService {

    CreateCouponResponse createCoupon(CreateCouponRequest request);
    ApplyCouponResponse applyCoupon(ApplyCouponRequest request);

}
