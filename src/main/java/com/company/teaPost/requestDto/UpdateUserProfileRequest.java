package com.company.teaPost.requestDto;

import lombok.Data;

@Data
public class UpdateUserProfileRequest {

    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
