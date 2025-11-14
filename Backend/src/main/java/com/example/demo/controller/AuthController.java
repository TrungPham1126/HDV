package com.example.demo.controller;

import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@RequestBody RegisterRequestDTO registerRequest) {
        try {

            UserResponseDTO response = authService.registerStudent(registerRequest);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}