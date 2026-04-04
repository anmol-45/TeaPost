package com.company.teaPost.controllers;


import com.company.teaPost.requestDto.SignInRequest;
import com.company.teaPost.requestDto.SignUpRequest;
import com.company.teaPost.responseDto.AuthResponse;

import com.company.teaPost.services.Impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody SignUpRequest user){

        return authService.registerUser(user);

    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> logInUser(@RequestBody SignInRequest user){

        return authService.logInUser(user);

    }
}
