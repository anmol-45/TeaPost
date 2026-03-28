package com.company.teaPost.requestDto;

import lombok.Data;

@Data
public class AddCartItemRequest {

    private String userId;
    private Long productId;
    private int quantity;
}
