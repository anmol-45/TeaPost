package com.company.teaPost.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDetails {

    private String cartItemId;

    private String productId;

    private String productName;

    private Double price;

    private Integer quantity;
}
