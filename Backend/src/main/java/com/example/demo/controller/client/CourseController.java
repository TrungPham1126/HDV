package com.example.demo.controller.client;

import com.example.demo.dto.CourseRequestDTO;
import com.example.demo.dto.CourseResponseDTO;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Import
import org.springframework.security.core.Authentication; // Import
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        List<CourseResponseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        CourseResponseDTO course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PreAuthorize("hasAuthority('ROLE_TEACHER')")

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(
            @RequestBody CourseRequestDTO request,
            Authentication authentication) {

        String teacherEmail = authentication.getName();

        // 2. Gọi Service để tạo Course
        CourseResponseDTO response = courseService.createCourse(request, teacherEmail);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}