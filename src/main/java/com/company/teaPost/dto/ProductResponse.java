package com.company.teaPost.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Long categoryId;

    private Double rating;

    private Integer stock;
}