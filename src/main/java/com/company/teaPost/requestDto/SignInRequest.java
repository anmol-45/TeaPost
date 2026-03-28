package com.company.teaPost.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInRequest {

    private String email;
    private String password;
}
