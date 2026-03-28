package com.company.teaPost.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AverageRatingResponse {

    private Long productId;
    private double averageRating;
    private int totalReviews;
}
