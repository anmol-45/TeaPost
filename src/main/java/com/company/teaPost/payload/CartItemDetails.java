package com.company.teaPost.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDetails {

    private Long productId;

    private String productName;

    private double price;

    private int quantity;
}
