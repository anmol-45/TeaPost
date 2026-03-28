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
                savedCoupon.getId(), savedCoupon.getCode());

        return CreateCouponResponse.builder()
                .id(savedCoupon.getId())
                .code(savedCoupon.getCode())
                .message("Coupon created successfully")
                .build();
    }

    @Override
    public ApplyCouponResponse applyCoupon(ApplyCouponRequest request) {

        log.info("Applying coupon code={} on orderId={}",
                request.getCouponCode(), request.getOrderId());

        // 1️⃣ Fetch Order
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> {
                    log.error("Order not found for orderId={}", request.getOrderId());
                    return new RuntimeException("Order not found");
                });

        // 2️⃣ Validate Order State
        if (!OrderStatus.CREATED.equals(order.getStatus())) {
            log.error("Coupon cannot be applied for orderId={}, status={}",
                    order.getId(), order.getStatus());
            throw new RuntimeException("Coupon can only be applied on CREATED orders");
        }

        // 3️⃣ Fetch Coupon
        Coupon coupon = couponRepository.findByCode(request.getCouponCode())
                .orElseThrow(() -> {
                    log.error("Invalid coupon code={}", request.getCouponCode());
                    return new RuntimeException("Invalid coupon code");
                });

        // 4️⃣ Validate Coupon
        if (!coupon.isActive()) {
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

        // 5️⃣ Calculate Discount
        double originalAmount = order.getTotalAmount();

        double discount = originalAmount * coupon.getDiscountPercentage() / 100;

        if (discount > coupon.getMaxDiscount()) {
            discount = coupon.getMaxDiscount();
        }

        double finalAmount = originalAmount - discount;

        // 6️⃣ Update Order
        order.setTotalAmount(finalAmount);
        orderRepository.save(order);

        log.info("Order updated with discounted amount orderId={}, finalAmount={}",
                order.getId(), finalAmount);

        // 7️⃣ Update Coupon Usage
        coupon.setUsedCount(coupon.getUsedCount() + 1);
        couponRepository.save(coupon);

        log.info("Coupon usage updated code={}, usedCount={}",
                coupon.getCode(), coupon.getUsedCount());

        // 8️⃣ Response
        return ApplyCouponResponse.builder()
                .originalAmount(originalAmount)
                .discount(discount)
                .finalAmount(finalAmount)
                .message("Coupon applied successfully")
                .build();
    }

}
