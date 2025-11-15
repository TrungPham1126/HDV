package com.example.demo;

import com.example.demo.entity.ERole;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner; // Import
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner { // <-- Thêm "implements CommandLineRunner"

	@Autowired // Tiêm (inject) RoleRepository để làm việc với DB
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// --- ĐÂY LÀ PHƯƠNG THỨC SẼ TỰ ĐỘNG CHẠY ---
	@Override
	public void run(String... args) throws Exception {

		System.out.println("--- Bắt đầu kiểm tra và tạo Roles ---");

		// 1. Kiểm tra và tạo ROLE_STUDENT
		if (roleRepository.findByName(ERole.ROLE_STUDENT).isEmpty()) {
			// Nếu không tìm thấy, tạo mới và lưu vào DB
			roleRepository.save(new Role(ERole.ROLE_STUDENT));
			System.out.println("Đã tạo: ROLE_STUDENT");
		}

		// 2. Kiểm tra và tạo ROLE_TEACHER
		if (roleRepository.findByName(ERole.ROLE_TEACHER).isEmpty()) {
			roleRepository.save(new Role(ERole.ROLE_TEACHER));
			System.out.println("Đã tạo: ROLE_TEACHER");
		}

		// 3. Kiểm tra và tạo ROLE_ADMIN
		if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
			roleRepository.save(new Role(ERole.ROLE_ADMIN));
			System.out.println("Đã tạo: ROLE_ADMIN");
		}

		System.out.println("--- Hoàn tất kiểm tra Roles ---");
	}
}