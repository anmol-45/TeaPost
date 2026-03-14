package com.company.teaPost.services.Impl;

import com.company.teaPost.dto.UpdateUserProfileRequestDto;
import com.company.teaPost.dto.UserProfileResponseDto;
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
    public UserProfileResponseDto getUserProfile(String email) {

        log.info("Fetching user profile email={}", email);

        User user = userRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        log.info("User profile fetched successfully email={}", email);

        return mapToResponse(user);
    }

    private UserProfileResponseDto mapToResponse(User user) {

        return UserProfileResponseDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .addressList(user.getShippingAddress())
                .build();
    }
    @Override
    public UserProfileResponseDto updateUserProfile(UpdateUserProfileRequestDto request) {

        log.info("Updating user profile email={}", request.getEmail());

        User user = userRepository.findById(request.getEmail())
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

}
