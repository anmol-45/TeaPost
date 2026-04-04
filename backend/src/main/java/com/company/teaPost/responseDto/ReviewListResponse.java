package com.company.teaPost.responseDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewListResponse {

    private String reviewId;
    private String userId;
    private Double rating;
    private String comment;
    private LocalDateTime createdAt;
}
