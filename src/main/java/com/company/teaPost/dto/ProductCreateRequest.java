package com.company.teaPost.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreateRequest {

    private String name;

    private String description;

    private Double price;

    private Long categoryId;

    private Integer stock;
}
