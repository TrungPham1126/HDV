package com.example.demo.service;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserResponseDTO;

public interface AuthService {

    UserResponseDTO registerStudent(RegisterRequestDTO registerRequest);

    UserResponseDTO registerTeacher(RegisterRequestDTO registerRequestDTO);

    LoginResponseDTO loginUser(LoginRequestDTO loginRequest);
}