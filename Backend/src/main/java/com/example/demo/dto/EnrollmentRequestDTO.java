package com.example.demo.dto;

// DTO này nhận ID của Khóa học mà Student muốn mua
public class EnrollmentRequestDTO {

    private Long courseId;

    // Getter
    public Long getCourseId() {
        return courseId;
    }

    // Setter
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}