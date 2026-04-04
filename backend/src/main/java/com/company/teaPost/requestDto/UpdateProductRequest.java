package com.company.teaPost.requestDto;

import lombok.Data;

@Data
public class UpdateProductRequest {

    private String name;
    private String description;
    private Double price;
    private String categoryId;
    private Integer stock;
}
