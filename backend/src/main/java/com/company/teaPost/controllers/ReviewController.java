package com.company.teaPost.controllers;

import com.company.teaPost.responseDto.AverageRatingResponse;
import com.company.teaPost.responseDto.ReviewListResponse;
import com.company.teaPost.requestDto.ReviewRequest;
import com.company.teaPost.responseDto.ReviewResponse;
import com.company.teaPost.services.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(
            @RequestBody ReviewRequest request) {

        log.info("API request received to add review for productId={}",
                request.getProductId());

        ReviewResponse response = reviewService.addReview(request);

        log.info("Review API completed for productId={}",
                request.getProductId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewListResponse>> getReviewsByProduct(
            @PathVariable String productId) {

        log.info("API request received to fetch reviews for productId={}", productId);

        List<ReviewListResponse> response =
                reviewService.getReviewsByProduct(productId);

        log.info("Returning {} reviews for productId={}",
                response.size(), productId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}/rating")
    public ResponseEntity<AverageRatingResponse> getAverageRating(
            @PathVariable String productId) {

        log.info("API request received for average rating productId={}", productId);

        AverageRatingResponse response =
                reviewService.getAverageRating(productId);

        log.info("Returning average rating for productId={}", productId);

        return ResponseEntity.ok(response);
    }


}
