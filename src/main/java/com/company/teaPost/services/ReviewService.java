package com.company.teaPost.services;

import com.company.teaPost.responseDto.AverageRatingResponse;
import com.company.teaPost.responseDto.ReviewListResponse;
import com.company.teaPost.requestDto.ReviewRequest;
import com.company.teaPost.responseDto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse addReview(ReviewRequest request);
    List<ReviewListResponse> getReviewsByProduct(String productId);
    AverageRatingResponse getAverageRating(String productId);


}

