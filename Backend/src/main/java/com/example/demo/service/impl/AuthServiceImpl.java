package com.example.demo.service.impl;

import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.util.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO registerStudent(RegisterRequestDTO registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {

            throw new BadRequestException("Lỗi: Email đã được sử dụng!");
        }

        User user = new User();
        user.setFullName(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy Role STUDENT."));

        roles.add(studentRole);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return convertToResponseDTO(savedUser);
    }
 @Override
    public UserResponseDTO registerTeacher(RegisterRequestDTO registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {

            throw new BadRequestException("Lỗi: Email đã được sử dụng!");
        }

        User user = new User();
        user.setFullName(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy Role TEACHER."));

        roles.add(teacherRole);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return convertToResponseDTO(savedUser);
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequest) {

        // 1. Xác thực người dùng (email, password)
        // Spring Security sẽ tự động gọi UserDetailsServiceImpl
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        // 2. Nếu xác thực thành công, lưu thông tin vào SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Tạo JWT token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // 4. Lấy thông tin UserDetails từ đối tượng Authentication
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // 5. Lấy danh sách Roles (dưới dạng String)
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 6. Trả về LoginResponseDTO (chứa token và thông tin user)
        return new LoginResponseDTO(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(), // getUsername() trả về email
                roles);
    }

    private UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());

        Set<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
        dto.setRoles(roleNames);

        return dto;
    }
}