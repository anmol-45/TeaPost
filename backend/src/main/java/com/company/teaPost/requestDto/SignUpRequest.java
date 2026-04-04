package com.company.teaPost.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
}