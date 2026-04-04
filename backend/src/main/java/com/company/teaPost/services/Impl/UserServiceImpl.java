package com.company.teaPost.services.Impl;

import com.company.teaPost.requestDto.UpdateUserProfileRequest;
import com.company.teaPost.responseDto.UserProfileResponse;
import com.company.teaPost.entities.User;
import com.company.teaPost.repositories.UserRepo;
import com.company.teaPost.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;

    @Override
    public UserProfileResponse getUserProfile(String email) {

        log.info("Fetching user profile email={}", email);

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        log.info("User profile fetched successfully email={}", email);

        return mapToResponse(user);
    }

    @Override
    public UserProfileResponse updateUserProfile(UpdateUserProfileRequest request) {

        log.info("Updating user profile email={}", request.getEmail());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        User updatedUser = userRepository.save(user);

        log.info("User profile updated successfully email={}", request.getEmail());

        return mapToResponse(updatedUser);
    }

    private UserProfileResponse mapToResponse(User user) {

        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .addressList(user.getShippingAddress())
                .build();
    }

}
