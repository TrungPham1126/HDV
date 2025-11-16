package com.example.demo.service;

import com.example.demo.dto.EnrollmentRequestDTO;
import com.example.demo.dto.EnrollmentResponseDTO;
import java.util.List;

public interface EnrollmentService {

    /**
     * Cho phép Student đang đăng nhập (studentEmail)
     * đăng ký (mua) một khóa học (request.courseId)
     */
    EnrollmentResponseDTO enrollCourse(EnrollmentRequestDTO request, String studentEmail);

    /**
     * Lấy danh sách các khóa học mà Student đang đăng nhập
     * (studentEmail) đã mua
     */
    List<EnrollmentResponseDTO> getMyEnrolledCourses(String studentEmail);
}