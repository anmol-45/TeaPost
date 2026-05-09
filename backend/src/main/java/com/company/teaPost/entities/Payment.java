package com.company.teaPost.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentId;

    private String orderId;

    private Double amount;

    private String paymentMode;

    private String status;

    private String razorpayPaymentId;

    private String razorpayOrderId;

    @Column(length = 1000)
    private String razorpaySignature;

    private LocalDateTime createdAt;
}
