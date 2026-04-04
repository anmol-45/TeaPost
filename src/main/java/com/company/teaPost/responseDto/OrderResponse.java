package com.company.teaPost.responseDto;

import com.company.teaPost.appConstants.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private String orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Double totalAmount;
}
