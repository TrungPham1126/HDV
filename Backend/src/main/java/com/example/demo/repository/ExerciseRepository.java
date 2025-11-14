package com.example.demo.repository;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    // Lấy tất cả bài tập của một khóa học
    List<Exercise> findByCourse(Course course);
}