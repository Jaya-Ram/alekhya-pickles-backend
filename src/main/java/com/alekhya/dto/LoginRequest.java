package com.alekhya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoginRequest {
    private String userName;
    private String password;
    private String email;
    private String address;
    private String mobileNumber;
    private String role;
}
