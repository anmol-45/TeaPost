package com.company.teaPost.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCouponResponse {

    private Long id;
    private String code;
    private String message;
}
