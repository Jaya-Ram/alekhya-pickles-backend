package com.alekhya.service;

import com.alekhya.dto.LoginRequest;
import com.alekhya.dto.LoginResponse;
import com.alekhya.dto.RegistrationRequest;
import com.alekhya.dto.RegistrationResponse;
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
            throw new RuntimeException("Username already exists");
        }

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
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
        // fetch user
        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(()-> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password.");
        }
        // Entity -> DTO conversion

        return mapper.map(user, LoginResponse.class);
    }
}
