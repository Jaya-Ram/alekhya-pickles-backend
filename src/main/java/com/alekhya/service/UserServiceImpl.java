package com.alekhya.service;

import com.alekhya.dto.LoginRequest;
import com.alekhya.dto.LoginResponse;
import com.alekhya.dto.RegistrationRequest;
import com.alekhya.dto.RegistrationResponse;
import com.alekhya.exception.InvalidCredentialsException;
import com.alekhya.exception.UserAlreadyExistsException;
import com.alekhya.model.User;
import com.alekhya.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public RegistrationResponse registerUser(RegistrationRequest request) {

        // Duplication check
        if(userRepository.existsByUserName(request.getUserName())){
            throw new UserAlreadyExistsException("Username already exists");
        }

        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("Email already exists");
        }

        // DTO -> Entity conversion
        User user = mapper.map(request, User.class);

        // default role
        user.setRole("USER");

        // Encrypting Password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        // Entity -> DTO conversion
        return mapper.map(savedUser, RegistrationResponse.class);
    }

    @Override
    public LoginResponse loginUser(LoginRequest request) {
        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(()-> new InvalidCredentialsException("Invalid username or password"));

        // password check
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid Password !");
        }

        return LoginResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .role(user.getRole())
                .message("Login Successful")
                .build();
    }
}
