package com.alekhya.controller;

import com.alekhya.dto.RegistrationRequest;
import com.alekhya.dto.RegistrationResponse;
import com.alekhya.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request){
        try{
            RegistrationResponse response = userService.registerUser(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
