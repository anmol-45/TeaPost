package com.company.teaPost.requestDto;


import lombok.Data;

@Data
public class ReviewRequest {

    private Long productId;
    private Long userId;
    private int rating;
    private String comment;
}
