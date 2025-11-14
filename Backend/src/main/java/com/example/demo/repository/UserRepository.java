package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm user bằng email (dùng cho đăng nhập và kiểm tra tồn tại)
    Optional<User> findByEmail(String email);

    // Kiểm tra email đã tồn tại
    Boolean existsByEmail(String email);
}