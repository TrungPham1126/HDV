package com.example.demo.repository;

import com.example.demo.entity.ClassT; // Import Entity Class của bạn

import com.example.demo.entity.Course;
import com.example.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassT, Long> {

    List<ClassT> findByCourse(Course course);

    List<ClassT> findByTeacher(User teacher);
}