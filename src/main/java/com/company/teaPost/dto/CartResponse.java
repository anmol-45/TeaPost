package com.company.teaPost.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartResponse {

    private Long cartId;

    private String userId;

    private List<CartItemDetails> items;

    private double totalAmount;
}
