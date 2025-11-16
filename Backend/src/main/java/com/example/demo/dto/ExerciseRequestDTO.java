package com.example.demo.dto;

// DTO dùng khi Teacher tạo hoặc cập nhật một Bài tập
public class ExerciseRequestDTO {

    private String title;

    // @Lob và columnDefinition="TEXT" sẽ được dùng trong Entity
    // DTO chỉ cần là String
    private String description; // Đề bài/Nội dung

    // Getters và Setters
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
}