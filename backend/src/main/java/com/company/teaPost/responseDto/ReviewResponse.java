package com.company.teaPost.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponse {

    private String reviewId;
    private String productId;
    private String userId;
    private Double rating;
    private String comment;
    private String message;
}
