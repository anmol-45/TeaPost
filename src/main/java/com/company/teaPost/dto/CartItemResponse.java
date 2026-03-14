package com.company.teaPost.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponse {

    private Long cartId;
    private Long productId;
    private int quantity;
}
