package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer; // Import

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // --- Chuyển Bean PasswordEncoder từ file Main sang đây ---
    // Đây là nơi "chuẩn" để quản lý các Bean liên quan đến bảo mật
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // --- THÊM DÒNG NÀY ---
                        // Cho phép tất cả mọi người truy cập API đăng ký
                        .requestMatchers("/api/auth/register/student").permitAll()

                        // (Sau này thêm /api/auth/login)
                        .requestMatchers("/api/auth/login").permitAll()

                        // Giữ lại cấu hình cũ của bạn (tạm thời)
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}