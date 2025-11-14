package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // Mật khẩu đã mã hóa

    @Lob // Mô tả dài về giáo viên
    private String bio; // Tiểu sử

    // Một Teacher có thể phụ trách nhiều Lớp học
    @OneToMany(mappedBy = "teacher")
    private Set<Class> classesTaught; // Các lớp đang dạy

    // Getters, Setters...
}