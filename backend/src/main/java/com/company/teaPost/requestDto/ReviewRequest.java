package com.company.teaPost.requestDto;


import lombok.Data;

@Data
public class ReviewRequest {

    private String productId;
    private String userId;
    private Double rating;
    private String comment;
}
