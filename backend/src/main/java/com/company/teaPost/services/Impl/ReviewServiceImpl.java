package com.company.teaPost.services.Impl;

import com.company.teaPost.responseDto.AverageRatingResponse;
import com.company.teaPost.responseDto.ReviewListResponse;
import com.company.teaPost.requestDto.ReviewRequest;
import com.company.teaPost.responseDto.ReviewResponse;
import com.company.teaPost.entities.Review;
import com.company.teaPost.repositories.ReviewRepository;
import com.company.teaPost.services.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public ReviewResponse addReview(ReviewRequest request) {

        log.info("Adding review for productId={}, userId={}",
                request.getProductId(), request.getUserId());

        // 1️⃣ Validate Rating
        if (request.getRating() < 1 || request.getRating() > 5) {
            log.error("Invalid rating={} for productId={}",
                    request.getRating(), request.getProductId());
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        // 2️⃣ Prevent Duplicate Review
        reviewRepository.findByProductIdAndUserId(
                request.getProductId(), request.getUserId()
        ).ifPresent(r -> {
            log.error("User {} already reviewed product {}",
                    request.getUserId(), request.getProductId());
            throw new RuntimeException("User already reviewed this product");
        });

        // 3️⃣ Create Review
        Review review = Review.builder()
                .productId(request.getProductId())
                .userId(request.getUserId())
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        Review savedReview = reviewRepository.save(review);

        log.info("Review created successfully reviewId={}", savedReview.getReviewId());

        // 4️⃣ Build Response
        return ReviewResponse.builder()
                .reviewId(savedReview.getReviewId())
                .productId(savedReview.getProductId())
                .userId(savedReview.getUserId())
                .rating(savedReview.getRating())
                .comment(savedReview.getComment())
                .message("Review added successfully")
                .build();
    }

    @Override
    public List<ReviewListResponse> getReviewsByProduct(String productId) {

        log.info("Fetching reviews for productId={}", productId);

        List<Review> reviews = reviewRepository.findByProductId(productId);

        if (reviews.isEmpty()) {
            log.warn("No reviews found for productId={}", productId);
        } else {
            log.info("Found {} reviews for productId={}", reviews.size(), productId);
        }

        return reviews.stream()
                .map(review -> ReviewListResponse.builder()
                        .reviewId(review.getReviewId())
                        .userId(review.getUserId())
                        .rating(review.getRating())
                        .comment(review.getComment())
                        .createdAt(review.getCreatedAt())
                        .build())
                .toList();
    }


    @Override
    public AverageRatingResponse getAverageRating(String productId) {

        log.info("Calculating average rating for productId={}", productId);

        List<Review> reviews = reviewRepository.findByProductId(productId);

        if (reviews.isEmpty()) {
            log.warn("No reviews found for productId={}", productId);

            return AverageRatingResponse.builder()
                    .productId(productId)
                    .averageRating(0.0)
                    .totalReviews(0)
                    .build();
        }

        double average = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        log.info("Average rating for productId={} is {} based on {} reviews",
                productId, average, reviews.size());

        return AverageRatingResponse.builder()
                .productId(productId)
                .averageRating(average)
                .totalReviews(reviews.size())
                .build();
    }


}

