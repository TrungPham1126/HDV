package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nhiều thanh toán (có thể) đến từ MỘT Học viên
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // MỘT thanh toán gắn với MỘT lượt đăng ký (Enrollment)
    // Giúp biết thanh toán này cho lớp nào
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", unique = true)
    private Enrollment enrollment;

    @Column(nullable = false)
    private BigDecimal amount; // Số tiền

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    private String paymentMethod; // VD: "VNPAY", "Chuyển khoản"

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // (Dùng Enum: PENDING, SUCCESS, FAILED)

    @PrePersist
    protected void onCreate() {
        paymentDate = LocalDateTime.now();
        status = PaymentStatus.PENDING; // Mặc định là đang chờ
    }
    // Getters, Setters...
}

// Tạo file Enum PaymentStatus.java
