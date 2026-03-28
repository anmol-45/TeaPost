package com.company.teaPost.responseDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDetailResponse {

    private Long orderId;
    private String userId;
    private String status;
    private Double totalAmount;
    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;
}

