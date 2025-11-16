package com.example.demo.controller.client;

import com.example.demo.dto.EnrollmentRequestDTO;
import com.example.demo.dto.EnrollmentResponseDTO;
import com.example.demo.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    // API MUA KHÓA HỌC
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<EnrollmentResponseDTO> enrollCourse(
            @RequestBody EnrollmentRequestDTO request,
            Authentication authentication) {

        String studentEmail = authentication.getName();
        EnrollmentResponseDTO response = enrollmentService.enrollCourse(request, studentEmail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // API XEM CÁC KHÓA HỌC CỦA TÔI
    @GetMapping("/my-courses")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<List<EnrollmentResponseDTO>> getMyCourses(
            Authentication authentication) {

        String studentEmail = authentication.getName();
        List<EnrollmentResponseDTO> myCourses = enrollmentService.getMyEnrolledCourses(studentEmail);
        return ResponseEntity.ok(myCourses);
    }
}