package com.example.demo.repository;

import com.example.demo.entity.Enrollment;
import com.example.demo.entity.User;
import com.example.demo.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // Tìm tất cả các lượt đăng ký của một học viên
    List<Enrollment> findByStudent(User student);

    // Tìm tất cả học viên đã đăng ký vào một lớp học
    List<Enrollment> findByEnrolledClass(Class enrolledClass);

    // Kiểm tra xem học viên đã đăng ký lớp này chưa
    boolean existsByStudentAndEnrolledClass(User student, Class enrolledClass);
}