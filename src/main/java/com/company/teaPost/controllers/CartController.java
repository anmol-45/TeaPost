package com.company.teaPost.controllers;

import com.company.teaPost.dto.AddCartItemRequest;
import com.company.teaPost.dto.CartItemResponse;
import com.company.teaPost.dto.CartResponse;
import com.company.teaPost.dto.UpdateCartItemRequest;
import com.company.teaPost.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public CartItemResponse addItemToCart(
            @RequestBody AddCartItemRequest request) {

        log.info("API request received to add item to cart");

        return cartService.addItemToCart(request);
    }

    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable String userId) {

        log.info("API request received to fetch cart userId={}", userId);

        return cartService.getCart(userId);
    }

    @PutMapping("/items/{itemId}")
    public CartItemResponse updateCartItem(
            @PathVariable Long itemId,
            @RequestBody UpdateCartItemRequest request) {

        log.info("API request received to update cart item itemId={}", itemId);

        return cartService.updateCartItem(itemId, request);
    }

    @DeleteMapping("/items/{itemId}")
    public String removeCartItem(@PathVariable Long itemId) {

        log.info("API request received to remove cart item itemId={}", itemId);

        return cartService.removeCartItem(itemId);
    }



}
