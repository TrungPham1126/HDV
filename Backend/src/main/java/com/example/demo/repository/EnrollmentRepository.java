package com.example.demo.repository;

import com.example.demo.entity.Enrollment;
import com.example.demo.entity.User;
import com.example.demo.entity.ClassT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudent(User student);

    List<Enrollment> findByEnrolledClass(ClassT enrolledClass);

    boolean existsByStudentAndEnrolledClass(User student, ClassT enrolledClass);
}