package com.alekhya.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {
    private Long userId;
    private String userName;
    private String email;
    private String address;
    private String mobileNumber;
    private String role;
}