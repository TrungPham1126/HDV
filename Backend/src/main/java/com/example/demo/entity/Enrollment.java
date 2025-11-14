package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nhiều lượt đăng ký thuộc về MỘT Học viên
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Nhiều lượt đăng ký thuộc về MỘT Lớp học
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private Class enrolledClass; // "class" là từ khóa, nên đổi tên trường

    @Column(nullable = false)
    private LocalDateTime enrolledAt; // Ngày giờ đăng ký

    // Một lượt đăng ký có thể có MỘT thanh toán (hoặc nhiều nếu trả góp)
    // Ở đây ta làm 1-1 cho đơn giản
    @OneToOne(mappedBy = "enrollment", cascade = CascadeType.ALL)
    private Payment payment;

    @PrePersist
    protected void onCreate() {
        enrolledAt = LocalDateTime.now();
    }
    // Getters, Setters...
}