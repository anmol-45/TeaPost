package com.company.teaPost.controllers;

import com.company.teaPost.requestDto.UpdateUserProfileRequest;
import com.company.teaPost.responseDto.UserProfileResponse;
import com.company.teaPost.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public UserProfileResponse getProfile(@RequestParam String email) {

        log.info("API request received to fetch profile email={}", email);

        return userService.getUserProfile(email);
    }

    @PutMapping("/profile")
    public UserProfileResponse updateProfile(
            @RequestBody UpdateUserProfileRequest request) {

        log.info("API request received to update profile email={}", request.getEmail());

        return userService.updateUserProfile(request);
    }

}
