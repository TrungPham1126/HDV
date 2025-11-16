package com.example.demo.service;


import com.example.demo.dto.UserProfileResponseDTO;
import com.example.demo.dto.UserUpdateRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // --- LOGIC CHO ADMIN ---

    // READ (Admin: Get All)
    public List<UserProfileResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserProfileResponseDTO::fromUser)
                .collect(Collectors.toList());
    }

    // READ (Admin: Get By ID)
    public UserProfileResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy User ID: " + id));
        return UserProfileResponseDTO.fromUser(user);
    }

    // UPDATE (Admin: Update Bất kỳ ai)
    public UserProfileResponseDTO updateUser(Long id, UserUpdateRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy User ID: " + id));

        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setBio(request.getBio());

        User updatedUser = userRepository.save(user);
        return UserProfileResponseDTO.fromUser(updatedUser);
    }

    // DELETE (Admin: Delete Bất kỳ ai)
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy User ID: " + id));
        
        // Cần xử lý các mối quan hệ (classesTaught, enrollments, payments) trước khi xóa
        // Ví dụ: Set các liên kết đó về null hoặc xóa các bản ghi liên quan
        // (Điều này RẤT quan trọng để tránh lỗi foreign key)

        userRepository.delete(user);
    }

    // --- LOGIC CHO USER (Tự xử lý) ---

    // READ (User: Get "me")
    public UserProfileResponseDTO getMyProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy User: " + email));
        return UserProfileResponseDTO.fromUser(user);
    }

    // UPDATE (User: Update "me")
    public UserProfileResponseDTO updateMyProfile(String email, UserUpdateRequestDTO request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy User: " + email));
        
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setBio(request.getBio());

        User updatedUser = userRepository.save(user);
        return UserProfileResponseDTO.fromUser(updatedUser);
    }
}