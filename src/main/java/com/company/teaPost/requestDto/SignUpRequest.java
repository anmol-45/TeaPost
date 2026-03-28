package com.company.teaPost.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {

    private String email;
    private String password;
    String firstName;
    String lastName;
}