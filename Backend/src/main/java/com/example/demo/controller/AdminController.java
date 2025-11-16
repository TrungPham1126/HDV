package com.example.demo.controller;

import com.example.demo.dto.UserProfileResponseDTO;
import com.example.demo.dto.UserUpdateRequestDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')") // Yêu cầu quyền ADMIN cho tất cả API
public class AdminController {

    @Autowired
    UserService userService;

    // READ (Admin xem tất cả)
    @GetMapping("/")
    public List<UserProfileResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // READ (Admin xem 1 người)
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDTO> getUserById(@PathVariable Long id) {
        UserProfileResponseDTO profile = userService.getUserById(id);
        return ResponseEntity.ok(profile);
    }

    // UPDATE (Admin cập nhật)
    @PutMapping("/{id}")
    public ResponseEntity<UserProfileResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO request) {
        UserProfileResponseDTO updatedProfile = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedProfile);
    }

    // DELETE (Admin xóa)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Đã xóa User ID: " + id);
        } catch (Exception e) {
            // (Nên bắt exception cụ thể, ví dụ DataIntegrityViolationException)
            return ResponseEntity.badRequest().body("Lỗi khi xóa User: " + e.getMessage());
        }
    }
}
