package com.example.demo.controller.client;

import com.example.demo.dto.ExerciseRequestDTO;
import com.example.demo.dto.ExerciseResponseDTO;
import com.example.demo.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api") // Dùng prefix chung là /api
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    // --- 1. (TEACHER) Thêm bài tập vào Khóa học ---
    // URL: POST http://localhost:8080/api/courses/1/exercises
    @PostMapping("/courses/{courseId}/exercises")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ResponseEntity<ExerciseResponseDTO> addExercise(
            @PathVariable Long courseId,
            @RequestBody ExerciseRequestDTO request,
            Authentication authentication) {

        String teacherEmail = authentication.getName();
        ExerciseResponseDTO response = exerciseService.addExerciseToCourse(courseId, request, teacherEmail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // --- 2. (TEACHER) Cập nhật bài tập ---
    // URL: PUT http://localhost:8080/api/exercises/1
    @PutMapping("/exercises/{exerciseId}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ResponseEntity<ExerciseResponseDTO> updateExercise(
            @PathVariable Long exerciseId,
            @RequestBody ExerciseRequestDTO request,
            Authentication authentication) {

        String teacherEmail = authentication.getName();
        ExerciseResponseDTO response = exerciseService.updateExercise(exerciseId, request, teacherEmail);
        return ResponseEntity.ok(response);
    }

    // --- 3. (TEACHER) Xóa bài tập ---
    // URL: DELETE http://localhost:8080/api/exercises/1
    @DeleteMapping("/exercises/{exerciseId}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ResponseEntity<Void> deleteExercise(
            @PathVariable Long exerciseId,
            Authentication authentication) {

        String teacherEmail = authentication.getName();
        exerciseService.deleteExercise(exerciseId, teacherEmail);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // --- 4. (STUDENT & TEACHER) Xem bài tập của Khóa học ---
    // URL: GET http://localhost:8080/api/courses/1/exercises
    @GetMapping("/courses/{courseId}/exercises")
    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_STUDENT')")
    public ResponseEntity<List<ExerciseResponseDTO>> getExercisesForCourse(
            @PathVariable Long courseId,
            Authentication authentication) {

        List<ExerciseResponseDTO> exercises = exerciseService.getExercisesForCourse(courseId, authentication);
        return ResponseEntity.ok(exercises);
    }
}