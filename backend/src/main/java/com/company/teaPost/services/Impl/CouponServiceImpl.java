package com.company.teaPost.services.Impl;

import com.company.teaPost.appConstants.OrderStatus;
import com.company.teaPost.requestDto.ApplyCouponRequest;
import com.company.teaPost.responseDto.ApplyCouponResponse;
import com.company.teaPost.requestDto.CreateCouponRequest;
import com.company.teaPost.responseDto.CreateCouponResponse;
import com.company.teaPost.entities.Coupon;
import com.company.teaPost.entities.Order;
import com.company.teaPost.repositories.CouponRepository;
import com.company.teaPost.repositories.OrderRepository;
import com.company.teaPost.services.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final OrderRepository orderRepository;

    @Override
    public CreateCouponResponse createCoupon(CreateCouponRequest request) {

        log.info("Creating coupon with code={}", request.getCode());

        // Validate duplicate
        couponRepository.findByCode(request.getCode())
                .ifPresent(c -> {
                    log.error("Coupon already exists with code={}", request.getCode());
                    throw new RuntimeException("Coupon already exists");
                });

        // Build entity
        Coupon coupon = Coupon.builder()
                .code(request.getCode())
                .discountPercentage(request.getDiscountPercentage())
                .maxDiscount(request.getMaxDiscount())
                .minOrderAmount(request.getMinOrderAmount())
                .expiryDate(request.getExpiryDate())
                .active(request.isActive())
                .usageLimit(request.getUsageLimit())
                .usedCount(0)
                .build();

        Coupon savedCoupon = couponRepository.save(coupon);

        log.info("Coupon created successfully with id={}, code={}",
                savedCoupon.getCouponId(), savedCoupon.getCode());

        return CreateCouponResponse.builder()
                .couponId(savedCoupon.getCouponId())
                .code(savedCoupon.getCode())
                .message("Coupon created successfully")
                .build();
    }

    @Override
    public ApplyCouponResponse applyCoupon(ApplyCouponRequest request) {

        log.info("Applying coupon code={} on orderId={}",
                request.getCouponCode(), request.getOrderId());

        // Fetch Order
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> {
                    log.error("Order not found for orderId={}", request.getOrderId());
                    return new RuntimeException("Order not found");
                });

        // Validate Order State
        if (!OrderStatus.CREATED.equals(order.getStatus())) {
            log.error("Coupon cannot be applied for orderId={}, status={}",
                    order.getOrderId(), order.getStatus());
            throw new RuntimeException("Coupon can only be applied on CREATED orders");
        }

        // Fetch Coupon
        Coupon coupon = couponRepository.findByCode(request.getCouponCode())
                .orElseThrow(() -> {
                    log.error("Invalid coupon code={}", request.getCouponCode());
                    return new RuntimeException("Invalid coupon code");
                });

        // Validate Coupon
        if (!coupon.getActive()) {
            log.error("Coupon inactive code={}", coupon.getCode());
            throw new RuntimeException("Coupon is inactive");
        }

        if (coupon.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.error("Coupon expired code={}", coupon.getCode());
            throw new RuntimeException("Coupon expired");
        }

        if (coupon.getUsedCount() >= coupon.getUsageLimit()) {
            log.error("Coupon usage limit reached code={}", coupon.getCode());
            throw new RuntimeException("Coupon usage limit reached");
        }

        if (order.getTotalAmount() < coupon.getMinOrderAmount()) {
            log.error("Order amount {} less than minimum required {}",
                    order.getTotalAmount(), coupon.getMinOrderAmount());
            throw new RuntimeException("Order amount too low for this coupon");
        }

        // Calculate Discount
        double originalAmount = order.getTotalAmount();

        double discount = originalAmount * coupon.getDiscountPercentage() / 100;

        if (discount > coupon.getMaxDiscount()) {
            discount = coupon.getMaxDiscount();
        }

        double finalAmount = originalAmount - discount;

        // Update Order
        order.setTotalAmount(finalAmount);
        orderRepository.save(order);

        log.info("Order updated with discounted amount orderId={}, finalAmount={}",
                order.getOrderId(), finalAmount);

        // Update Coupon Usage
        coupon.setUsedCount(coupon.getUsedCount() + 1);
        couponRepository.save(coupon);

        log.info("Coupon usage updated code={}, usedCount={}",
                coupon.getCode(), coupon.getUsedCount());

        // Response
        return ApplyCouponResponse.builder()
                .couponId(coupon.getCouponId())
                .originalAmount(originalAmount)
                .discount(discount)
                .finalAmount(finalAmount)
                .message("Coupon applied successfully")
                .build();
    }

}
