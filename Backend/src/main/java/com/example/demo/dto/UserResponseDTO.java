package com.example.demo.dto;

import java.util.Set;

// DTO trả về thông tin User (KHÔNG BAO GỒM MẬT KHẨU)
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Set<String> roles; // Chỉ trả về tên các role

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}