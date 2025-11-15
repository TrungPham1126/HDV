package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
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
    public ResponseEntity<UserResponseDTO> registerStudent(
            @RequestBody RegisterRequestDTO registerRequest) {

        UserResponseDTO response = authService.registerStudent(registerRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/register/teacher")
    public ResponseEntity<UserResponseDTO> registerTeacher(
            @RequestBody RegisterRequestDTO registerRequest) {

        UserResponseDTO response = authService.registerStudent(registerRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticateUser(
            @RequestBody LoginRequestDTO loginRequest) {

        LoginResponseDTO loginResponse = authService.loginUser(loginRequest);

        return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
    }
}
