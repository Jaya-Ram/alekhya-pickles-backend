package com.alekhya.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private Long userId;
    private String userName;
    private String email;
    private String role;
    private String message;
}