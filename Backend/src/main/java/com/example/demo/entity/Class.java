package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String className;
    private BigDecimal price;
    private Integer maxStudents;

    // --- Quan hệ ---

    // Nhiều Lớp học có thể thuộc MỘT Khóa học (Course)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course; // Thuộc môn học nào

    // Nhiều Lớp học (có thể) được phụ trách bởi MỘT Giáo viên
    // (Theo mô tả của bạn: "mỗi lớp do 1 giáo viên phụ trách")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher; // Giáo viên nào phụ trách

    // Một Lớp học có nhiều lượt đăng ký
    @OneToMany(mappedBy = "enrolledClass")
    private Set<Enrollment> enrollments;

    // Một Lớp học có nhiều buổi học (lịch học)
    @OneToMany(mappedBy = "classEntry", cascade = CascadeType.ALL)
    private Set<Schedule> schedules;

    // Getters, Setters...
}