package com.company.teaPost.controllers;

import com.company.teaPost.requestDto.ApplyCouponRequest;
import com.company.teaPost.responseDto.ApplyCouponResponse;
import com.company.teaPost.requestDto.CreateCouponRequest;
import com.company.teaPost.responseDto.CreateCouponResponse;
import com.company.teaPost.services.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
@Slf4j
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CreateCouponResponse> createCoupon(
            @RequestBody CreateCouponRequest request) {

        log.info("API request received to create coupon code={}", request.getCode());

        CreateCouponResponse response = couponService.createCoupon(request);

        log.info("API response sent for coupon code={}", request.getCode());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/apply")
    public ResponseEntity<ApplyCouponResponse> applyCoupon(
            @RequestBody ApplyCouponRequest request) {

        log.info("API request received to apply coupon code={}", request.getCouponCode());

        ApplyCouponResponse response = couponService.applyCoupon(request);

        log.info("Coupon applied successfully for orderId={}", request.getOrderId());

        return ResponseEntity.ok(response);
    }

}

