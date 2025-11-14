package com.example.demo.repository;

import com.example.demo.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // Tìm giáo viên bằng email
    Optional<Teacher> findByEmail(String email);
}