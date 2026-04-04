package com.company.teaPost.responseDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartItemResponse {

    private List<String> cartItemId;
    private String cartId;
    private String productId;
    private Integer quantity;
}
