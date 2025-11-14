package com.example.demo.service;

import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserResponseDTO;

public interface AuthService {

    UserResponseDTO registerStudent(RegisterRequestDTO registerRequest);

}