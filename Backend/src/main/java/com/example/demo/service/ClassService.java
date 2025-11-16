package com.example.demo.service;

import com.example.demo.dto.ClassRequestDTO;
import com.example.demo.dto.ClassResponseDTO;
import java.util.List;

public interface ClassService {
    List<ClassResponseDTO> getAllClasses();

    ClassResponseDTO getClassById(Long id);

    ClassResponseDTO createClass(ClassRequestDTO request, String teacherEmail);
}