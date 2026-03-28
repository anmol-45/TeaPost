package com.company.teaPost.services;

import com.company.teaPost.requestDto.AddCartItemRequest;
import com.company.teaPost.responseDto.CartItemResponse;
import com.company.teaPost.responseDto.CartResponse;
import com.company.teaPost.requestDto.UpdateCartItemRequest;

public interface CartService {

    CartItemResponse addItemToCart(AddCartItemRequest request);
    CartResponse getCart(String userId);
    CartItemResponse updateCartItem(Long itemId, UpdateCartItemRequest request);
    String removeCartItem(Long itemId);




}
