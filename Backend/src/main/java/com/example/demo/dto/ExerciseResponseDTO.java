package com.example.demo.dto;

// DTO dùng để trả về thông tin Bài tập
public class ExerciseResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Long courseId; // ID của Khóa học chứa bài tập này

    // Getters và Setters
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}