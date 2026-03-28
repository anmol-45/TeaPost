package com.company.teaPost.responseDto;

import lombok.Builder;
import lombok.Data;

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