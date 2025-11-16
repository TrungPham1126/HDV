package com.example.demo.repository;

import com.example.demo.entity.Course; // Import
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.User; // Import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentAndCourse(User student, Course course);

    List<Enrollment> findByStudent(User student);

    // --- THÊM HÀM NÀY ---
    // Đếm số lượng 'Enrollment' (lượt đăng ký) cho một 'Course'
    Integer countByCourse(Course course);
}