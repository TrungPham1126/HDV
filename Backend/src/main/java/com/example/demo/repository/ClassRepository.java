package com.example.demo.repository;

import com.example.demo.entity.Class; // Import Entity Class của bạn

import com.example.demo.entity.Course;
import com.example.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    // Tìm tất cả các lớp học thuộc một khóa học (Course)
    List<Class> findByCourse(Course course);

    // Tìm tất cả các lớp học do một giáo viên phụ trách
    List<Class> findByTeacher(User teacher);
}