package com.company.teaPost.controllers;

import com.company.teaPost.dto.UpdateUserProfileRequestDto;
import com.company.teaPost.dto.UserProfileResponseDto;
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
    public UserProfileResponseDto getProfile(@RequestParam String email) {

        log.info("API request received to fetch profile email={}", email);

        return userService.getUserProfile(email);
    }

    @PutMapping("/profile")
    public UserProfileResponseDto updateProfile(
            @RequestBody UpdateUserProfileRequestDto request) {

        log.info("API request received to update profile email={}", request.getEmail());

        return userService.updateUserProfile(request);
    }

}
