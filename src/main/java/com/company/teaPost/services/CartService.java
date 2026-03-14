package com.company.teaPost.services;

import com.company.teaPost.dto.AddCartItemRequest;
import com.company.teaPost.dto.CartItemResponse;
import com.company.teaPost.dto.CartResponse;
import com.company.teaPost.dto.UpdateCartItemRequest;

public interface CartService {

    CartItemResponse addItemToCart(AddCartItemRequest request);
    CartResponse getCart(String userId);
    CartItemResponse updateCartItem(Long itemId, UpdateCartItemRequest request);
    String removeCartItem(Long itemId);




}
