package com.example.demo.service.impl; // Tạo package này

import com.example.demo.dto.EnrollmentRequestDTO;
import com.example.demo.dto.EnrollmentResponseDTO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EnrollmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    // --- Hàm Helper ---
    private EnrollmentResponseDTO convertToResponseDTO(Enrollment enrollment) {
        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
        dto.setEnrollmentId(enrollment.getId());
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        dto.setStudentId(enrollment.getStudent().getId());
        dto.setCourseId(enrollment.getCourse().getId());
        dto.setCourseTitle(enrollment.getCourse().getTitle());
        return dto;
    }

    // --- STUDENT Mua Khóa học ---
    @Override
    public EnrollmentResponseDTO enrollCourse(EnrollmentRequestDTO request, String studentEmail) {
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User không tồn tại."));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Khóa học không tồn tại."));

        // Kiểm tra đã mua chưa
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new BadRequestException("Bạn đã đăng ký khóa học này rồi.");
        }

        // (Đây là nơi để gọi API thanh toán Payment)

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return convertToResponseDTO(savedEnrollment);
    }

    // --- STUDENT Lấy các khóa đã mua ---
    @Override
    public List<EnrollmentResponseDTO> getMyEnrolledCourses(String studentEmail) {
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User không tồn tại."));

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);

        return enrollments.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
}