package com.company.teaPost.services.Impl;

import com.company.teaPost.responseDto.*;
import com.company.teaPost.entities.Cart;
import com.company.teaPost.entities.CartItem;
import com.company.teaPost.entities.Product;
import com.company.teaPost.payload.CartItemDetails;
import com.company.teaPost.repositories.CartItemRepository;
import com.company.teaPost.repositories.CartRepository;
import com.company.teaPost.repositories.ProductRepository;
import com.company.teaPost.requestDto.AddCartItemRequest;
import com.company.teaPost.requestDto.UpdateCartItemRequest;
import com.company.teaPost.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public CartItemResponse addItemToCart(AddCartItemRequest request) {

        log.info("Adding item to cart userId={} productId={}",
                request.getUserId(), request.getProductId());

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUserId(request.getUserId())
                .orElseGet(() -> {
                    log.info("Cart not found. Creating new cart for user={}", request.getUserId());

                    return cartRepository.save(
                            Cart.builder()
                                    .userId(request.getUserId())
                                    .createdAt(LocalDateTime.now())
                                    .updatedAt(LocalDateTime.now())
                                    .build()
                    );
                });

        CartItem item = CartItem.builder()
                .productId(product.getProductId())
                .quantity(request.getQuantity())
                .price(product.getPrice())
                .cart(cart)
                .build();

        cart.getItems().add(item);

        cart = cartRepository.save(cart);

        log.info("Product added to cart cartId={}", cart.getCartId());
        log.info("cartItem added to cart cartId={}", item.getCartItemId());

        return CartItemResponse.builder()
                .cartItemId(cart.getItems().stream().map(CartItem::getCartItemId).toList())
                .cartId(cart.getCartId())
                .productId(product.getProductId())
                .quantity(request.getQuantity())
                .build();
    }

    @Override
    public CartResponse getCart(String userId) {

        log.info("Fetching cart for userId={}", userId);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItemDetails> items = cart.getItems()
                .stream()
                .map(item -> {

                    Product product = productRepository
                            .findById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    return CartItemDetails.builder()
                            .cartItemId(item.getCartItemId())
                            .productId(item.getProductId())
                            .productName(product.getName())
                            .price(item.getPrice())
                            .quantity(item.getQuantity())
                            .build();
                })
                .toList();

        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        log.info("Cart fetched successfully cartId={}", cart.getCartId());

        return CartResponse.builder()
                .cartId(cart.getCartId())
                .userId(userId)
                .items(items)
                .totalAmount(total)
                .build();
    }

    @Override
    public CartItemResponse updateCartItem(String cartItemId, UpdateCartItemRequest request) {

        log.info("Updating cart item itemId={} newQuantity={}", cartItemId, request.getQuantity());

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(request.getQuantity());

        //need to check if the cart item value doesn't exist the current stock
        CartItem updatedItem = cartItemRepository.save(cartItem);

        log.info("Cart item updated successfully itemId={}", updatedItem.getCartItemId());

        return CartItemResponse.builder()
                .cartId(updatedItem.getCart().getCartId())
                .cartItemId(Collections.singletonList(updatedItem.getCartItemId()))
                .productId(updatedItem.getProductId())
                .quantity(updatedItem.getQuantity())
                .build();
    }

    @Override
    public String removeCartItem(String cartItemId) {

        log.info("Removing cart item itemId={}", cartItemId);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(cartItem);

        log.info("Cart item removed successfully itemId={}", cartItem);
        return "Cart item removed successfully";
    }


}
