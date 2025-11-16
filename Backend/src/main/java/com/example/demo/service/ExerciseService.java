package com.example.demo.service;

import com.example.demo.dto.ExerciseRequestDTO;
import com.example.demo.dto.ExerciseResponseDTO;
import org.springframework.security.core.Authentication; // Import
import java.util.List;

public interface ExerciseService {

    // --- Dành cho TEACHER ---

    // Thêm bài tập vào Khóa học
    ExerciseResponseDTO addExerciseToCourse(Long courseId, ExerciseRequestDTO request, String teacherEmail);

    // Cập nhật bài tập
    ExerciseResponseDTO updateExercise(Long exerciseId, ExerciseRequestDTO request, String teacherEmail);

    // Xóa bài tập
    void deleteExercise(Long exerciseId, String teacherEmail);

    // --- Dành cho STUDENT (đã mua) và TEACHER ---

    // Lấy tất cả bài tập của một Khóa học
    List<ExerciseResponseDTO> getExercisesForCourse(Long courseId, Authentication authentication);
}