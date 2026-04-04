
package com.company.teaPost.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products",
        indexes = {
                @Index(name = "idx_category", columnList = "categoryId"),
                @Index(name = "idx_price", columnList = "price"),
                @Index(name = "idx_rating", columnList = "rating"),
                @Index(name = "idx_name", columnList = "name")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId;

    private String name;

    private String description;

    private Double price;

    private String categoryId;

    private Double rating;

    private Integer stock;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}