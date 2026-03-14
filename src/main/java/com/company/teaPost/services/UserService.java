package com.company.teaPost.services;

import com.company.teaPost.dto.UpdateUserProfileRequestDto;
import com.company.teaPost.dto.UserProfileResponseDto;

public interface UserService {

    UserProfileResponseDto getUserProfile(String email);
    UserProfileResponseDto updateUserProfile(UpdateUserProfileRequestDto request);


}
