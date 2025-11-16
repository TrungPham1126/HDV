package com.example.demo.service.impl;

import com.example.demo.dto.ExerciseRequestDTO;
import com.example.demo.dto.ExerciseResponseDTO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Exercise;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.ExerciseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ExerciseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // --- Hàm Helper (Chuyển Entity -> DTO) ---
    private ExerciseResponseDTO convertToResponseDTO(Exercise exercise) {
        ExerciseResponseDTO dto = new ExerciseResponseDTO();
        dto.setId(exercise.getId());
        dto.setTitle(exercise.getTitle());
        dto.setDescription(exercise.getDescription());
        if (exercise.getCourse() != null) {
            dto.setCourseId(exercise.getCourse().getId());
        }
        return dto;
    }

    // --- HÀM BẢO MẬT (Kiểm tra quyền Teacher) ---
    private Course checkTeacherOwnership(Long courseId, String teacherEmail) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Khóa học với ID: " + courseId));

        // KIỂM TRA QUAN TRỌNG: Email của Teacher trong Token có TRÙNG với email của
        // Teacher sở hữu Khóa học không?
        if (!course.getTeacher().getEmail().equals(teacherEmail)) {
            throw new AccessDeniedException("Bạn không có quyền chỉnh sửa bài tập của khóa học này.");
        }
        return course;
    }

    // --- 1. (TEACHER) Thêm Bài tập ---
    @Override
    public ExerciseResponseDTO addExerciseToCourse(Long courseId, ExerciseRequestDTO request, String teacherEmail) {
        // Kiểm tra xem Teacher có sở hữu Course này không
        Course course = checkTeacherOwnership(courseId, teacherEmail);

        Exercise exercise = new Exercise();
        exercise.setCourse(course); // Gán vào Khóa học
        exercise.setTitle(request.getTitle());
        exercise.setDescription(request.getDescription());

        Exercise savedExercise = exerciseRepository.save(exercise);
        return convertToResponseDTO(savedExercise);
    }

    // --- 2. (TEACHER) Cập nhật Bài tập ---
    @Override
    public ExerciseResponseDTO updateExercise(Long exerciseId, ExerciseRequestDTO request, String teacherEmail) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Bài tập với ID: " + exerciseId));

        // Kiểm tra quyền sở hữu (thông qua Course)
        checkTeacherOwnership(exercise.getCourse().getId(), teacherEmail);

        // Cập nhật thông tin
        exercise.setTitle(request.getTitle());
        exercise.setDescription(request.getDescription());

        Exercise updatedExercise = exerciseRepository.save(exercise);
        return convertToResponseDTO(updatedExercise);
    }

    // --- 3. (TEACHER) Xóa Bài tập ---
    @Override
    public void deleteExercise(Long exerciseId, String teacherEmail) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Bài tập với ID: " + exerciseId));

        // Kiểm tra quyền sở hữu
        checkTeacherOwnership(exercise.getCourse().getId(), teacherEmail);

        exerciseRepository.delete(exercise);
    }

    // --- 4. (STUDENT/TEACHER) Xem Bài tập ---
    @Override
    public List<ExerciseResponseDTO> getExercisesForCourse(Long courseId, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User không tồn tại."));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Khóa học không tồn tại."));

        // --- KIỂM TRA BẢO MẬT (ĐA QUYỀN) ---
        boolean isTeacherOfCourse = course.getTeacher().getId().equals(user.getId());
        boolean isEnrolledStudent = enrollmentRepository.existsByStudentAndCourse(user, course);

        // Nếu KHÔNG PHẢI là Teacher VÀ CŨNG KHÔNG PHẢI là Student đã mua
        if (!isTeacherOfCourse && !isEnrolledStudent) {
            throw new AccessDeniedException("Bạn không có quyền xem bài tập của khóa học này.");
        }

        // Nếu qua được, lấy bài tập
        List<Exercise> exercises = exerciseRepository.findByCourse(course);
        return exercises.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
}