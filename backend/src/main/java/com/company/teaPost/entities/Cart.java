package com.company.teaPost.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cartId ;

    private String userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();
}
