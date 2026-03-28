package com.company.teaPost.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponse {

    private Long reviewId;
    private Long productId;
    private Long userId;
    private int rating;
    private String comment;
    private String message;
}
