package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal; // Import
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    // --- THÊM CÁC TRƯỜNG NÀY ---

    @Column(nullable = false)
    private BigDecimal price; // Giá của khóa học

    // Nhiều Khóa học được tạo bởi MỘT User (Teacher)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher; // Người tạo/dạy

    // --- XÓA TRƯỜNG NÀY ---
    // @OneToMany(mappedBy = "course")
    // private Set<ClassT> classes; // <-- XÓA BỎ

    // --- CÁC TRƯỜNG NỘI DUNG ---
    @OneToMany(mappedBy = "course")
    private Set<Video> videos;

    @OneToMany(mappedBy = "course")
    private Set<Exercise> exercises;

    // --- CÁC LƯỢT MUA ---
    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollments;

    // Getters và Setters...
    // (Bao gồm đầy đủ cho price, teacher, videos, exercises...)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
    // ...
}