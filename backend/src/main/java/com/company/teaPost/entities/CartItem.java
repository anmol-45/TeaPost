package com.company.teaPost.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cartItemId;

    private String productId;

    private Integer quantity;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;
}
