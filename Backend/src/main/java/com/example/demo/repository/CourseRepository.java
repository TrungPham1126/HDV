package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Tìm các khóa học có tiêu đề chứa một chuỗi nào đó
    List<Course> findByTitleContaining(String title);
}