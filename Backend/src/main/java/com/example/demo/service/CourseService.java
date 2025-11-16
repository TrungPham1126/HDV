package com.example.demo.service;

import com.example.demo.dto.CourseRequestDTO;
import com.example.demo.dto.CourseResponseDTO;
import java.util.List;

public interface CourseService {
    // API CÔNG KHAI
    List<CourseResponseDTO> getAllCourses();

    CourseResponseDTO getCourseById(Long id);

    // API BẢO VỆ
    CourseResponseDTO createCourse(CourseRequestDTO request, String teacherEmail);
}