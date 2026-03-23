package com.alekhya.service;

import com.alekhya.dto.LoginRequest;
import com.alekhya.dto.LoginResponse;
import com.alekhya.dto.RegistrationRequest;
import com.alekhya.dto.RegistrationResponse;

public interface IUserService {
    RegistrationResponse registerUser(RegistrationRequest request);
    LoginResponse loginUser(LoginRequest request);
}
