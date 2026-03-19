package com.alekhya.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponse {
    private Long userId;
    private String userName;
    private String email;
    private String address;
    private String mobileNumber;
    private String role;
}