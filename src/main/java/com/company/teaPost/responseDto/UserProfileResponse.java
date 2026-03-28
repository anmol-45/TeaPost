package com.company.teaPost.responseDto;

import com.company.teaPost.entities.ShippingAddress;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserProfileResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;
    private LocalDateTime createdAt;
    private List<ShippingAddress> addressList;
}
