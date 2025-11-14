package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // Mật khẩu đã mã hóa

    private String phoneNumber;

    // Một Student có thể đăng ký nhiều Lớp học (thông qua Enrollment)
    @OneToMany(mappedBy = "student")
    private Set<Enrollment> enrollments;

    // Một Student có thể có nhiều lần thanh toán
    @OneToMany(mappedBy = "student")
    private Set<Payment> payments;

    // Getters, Setters...
}