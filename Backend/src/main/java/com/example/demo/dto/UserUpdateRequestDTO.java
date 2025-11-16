package com.example.demo.dto;

import lombok.Data;

@Data // Dùng Lombok cho nhanh
public class UserUpdateRequestDTO {
	private String fullName;
    private String phoneNumber;
    private String bio;
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
    
}