package com.company.teaPost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInRequestDto {

    private String email;
    private String password;
}
