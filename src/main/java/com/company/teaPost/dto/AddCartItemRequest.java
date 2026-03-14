package com.company.teaPost.dto;

import lombok.Data;

@Data
public class AddCartItemRequest {

    private String userId;
    private Long productId;
    private int quantity;
}
