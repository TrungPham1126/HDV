package com.example.demo.repository;

import com.example.demo.entity.Payment;

import com.example.demo.entity.PaymentStatus;
import com.example.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Lấy tất cả thanh toán của một học viên
    List<Payment> findByStudent(User student);

    // Lấy các thanh toán theo trạng thái (PENDING, SUCCESS, FAILED)
    List<Payment> findByStatus(PaymentStatus status);
}