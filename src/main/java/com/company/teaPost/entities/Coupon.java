package com.company.teaPost.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private Double discountPercentage;

    private Double maxDiscount;

    private Double minOrderAmount;

    private LocalDateTime expiryDate;

    private boolean active;

    private int usageLimit;

    private int usedCount;
}

