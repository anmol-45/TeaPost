package com.company.teaPost.requestDto;

import lombok.Data;

@Data
public class AddCartItemRequest {

    private String userId;
    private String productId;
    private Integer quantity;
}
