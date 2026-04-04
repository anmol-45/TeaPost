package com.company.teaPost.responseDto;

import com.company.teaPost.payload.CartItemDetails;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartResponse {

    private String cartId;

    private String userId;

    private List<CartItemDetails> items;

    private double totalAmount;
}
