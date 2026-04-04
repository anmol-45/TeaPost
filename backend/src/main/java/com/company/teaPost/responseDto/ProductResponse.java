    package com.company.teaPost.responseDto;

    import lombok.Builder;
    import lombok.Data;

    @Data
    @Builder
    public class ProductResponse {

        private String productId;

        private String name;

        private String description;

        private Double price;

        private String categoryId;

        private Double rating;

        private Integer stock;
    }