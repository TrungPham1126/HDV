package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // Tên môn học (VD: Lập trình Java)

    @Lob
    private String description;

    // Một Course (môn học) có thể được mở thành nhiều Lớp học (Class)
    @OneToMany(mappedBy = "course")
    private Set<Class> classes;

    // --- Giữ lại từ yêu cầu gốc ---
    // Một Course sẽ có bộ video bài giảng chung
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Video> videos;

    // Một Course sẽ có bộ bài tập chung
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Exercise> exercises;

    // Getters, Setters...
}
