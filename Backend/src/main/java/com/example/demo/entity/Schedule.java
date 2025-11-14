package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nhiều lịch học thuộc MỘT Lớp học
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private Class classEntry; // Buổi học này của lớp nào

    @Column(nullable = false)
    private LocalDateTime startTime; // Giờ bắt đầu

    @Column(nullable = false)
    private LocalDateTime endTime; // Giờ kết thúc

    private String room; // Phòng học (nếu có)
    private String topic; // Chủ đề buổi học (VD: "Bài 1: Giới thiệu")

    // Getters, Setters...
}