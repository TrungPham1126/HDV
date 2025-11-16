package com.example.demo.service.impl;

import com.example.demo.dto.ClassRequestDTO;
import com.example.demo.dto.ClassResponseDTO;
import com.example.demo.entity.ClassT; // Import Entity Class
import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ClassRepository;
import com.example.demo.repository.CourseRepository; // Cần thiết
import com.example.demo.repository.UserRepository; // Cần thiết
import com.example.demo.service.ClassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    // --- Hàm Helper (Chuyển Entity -> ResponseDTO) ---
    private ClassResponseDTO convertToResponseDTO(ClassT clazz) {
        ClassResponseDTO dto = new ClassResponseDTO();
        dto.setId(clazz.getId());
        dto.setClassName(clazz.getClassName());
        dto.setPrice(clazz.getPrice());
        dto.setMaxStudents(clazz.getMaxStudents());

        // Liên kết với Course và Teacher
        if (clazz.getCourse() != null) {
            dto.setCourseId(clazz.getCourse().getId());
            dto.setCourseTitle(clazz.getCourse().getTitle());
        }
        if (clazz.getTeacher() != null) {
            dto.setTeacherName(clazz.getTeacher().getFullName());
        }
        return dto;
    }

    @Override
    public List<ClassResponseDTO> getAllClasses() {
        List<ClassT> classes = classRepository.findAll();
        return classes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClassResponseDTO getClassById(Long id) {
        ClassT clazz = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Lớp học với ID: " + id));
        return convertToResponseDTO(clazz);
    }

    @Override
    public ClassResponseDTO createClass(ClassRequestDTO request, String teacherEmail) {

        // 1. Tìm User (Giáo viên) từ Email trong JWT
        User teacher = userRepository.findByEmail(teacherEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Lỗi: User từ Token không tồn tại."));

        // 2. Tìm Course mà Lớp này thuộc về
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy Khóa học với ID: " + request.getCourseId()));

        // 3. Tạo Entity
        ClassT clazz = new ClassT();
        clazz.setClassName(request.getClassName());
        clazz.setPrice(request.getPrice());
        clazz.setMaxStudents(request.getMaxStudents());
        clazz.setCourse(course); // Gán Course
        clazz.setTeacher(teacher); // Gán Teacher từ Token

        // 4. Lưu
        ClassT savedClass = classRepository.save(clazz);

        return convertToResponseDTO(savedClass);
    }
}