package com.company.teaPost.dto;

import lombok.Data;

@Data
public class UpdateUserProfileRequestDto {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
