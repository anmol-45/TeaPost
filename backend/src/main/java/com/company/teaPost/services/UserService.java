package com.company.teaPost.services;

import com.company.teaPost.requestDto.UpdateUserProfileRequest;
import com.company.teaPost.responseDto.UserProfileResponse;

public interface UserService {

    UserProfileResponse getUserProfile(String email);
    UserProfileResponse updateUserProfile(UpdateUserProfileRequest request);


}
