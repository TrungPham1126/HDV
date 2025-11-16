package com.example.demo.controller;

import com.example.demo.dto.UserProfileResponseDTO;
import com.example.demo.dto.UserUpdateRequestDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("isAuthenticated()") // Yêu cầu đăng nhập cho tất cả API trong này
public class UserController {

    @Autowired
    UserService userService;

    // READ (User tự lấy thông tin của mình)
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> getMyProfile(Authentication authentication) {
        // authentication.getName() sẽ là email (vì ta cấu hình trong UserDetailsService)
        String myEmail = authentication.getName();
        UserProfileResponseDTO profile = userService.getMyProfile(myEmail);
        return ResponseEntity.ok(profile);
    }

    // UPDATE (User tự cập nhật thông tin)
    @PutMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> updateMyProfile(Authentication authentication, @RequestBody UserUpdateRequestDTO request) {
        String myEmail = authentication.getName();
        UserProfileResponseDTO updatedProfile = userService.updateMyProfile(myEmail, request);
        return ResponseEntity.ok(updatedProfile);
    }
}
