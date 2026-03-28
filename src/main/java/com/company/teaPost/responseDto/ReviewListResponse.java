package com.company.teaPost.responseDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewListResponse {

    private Long reviewId;
    private Long userId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
