package com.example.demo.service.impl;

import com.example.demo.dto.CourseRequestDTO;
import com.example.demo.dto.CourseResponseDTO;
import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    // --- Hàm Helper (Chuyển Entity -> ResponseDTO) ---
    private CourseResponseDTO convertToResponseDTO(Course course) {
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());

        return dto;
    }

    @Override
    public List<CourseResponseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + id));
        return convertToResponseDTO(course);
    }

    // --- CREATE (Bảo vệ) ---
    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO request, String userEmail) {

        User creator = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Lỗi: User từ Token không tồn tại."));

        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());

        Course savedCourse = courseRepository.save(course);
        return convertToResponseDTO(savedCourse);
    }
}