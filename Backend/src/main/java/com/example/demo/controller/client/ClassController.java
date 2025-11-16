package com.example.demo.controller.client;

// Import DTOs từ package "clazz"
import com.example.demo.dto.ClassRequestDTO;
import com.example.demo.dto.ClassResponseDTO;
// Import Service từ package ""
import com.example.demo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping
    public ResponseEntity<List<ClassResponseDTO>> getAllClasses() {
        List<ClassResponseDTO> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassResponseDTO> getClassById(@PathVariable Long id) {
        ClassResponseDTO clazz = classService.getClassById(id);
        return ResponseEntity.ok(clazz);
    }

    @PreAuthorize("hasAuthority('ROLE_TEACHER')")

    @PostMapping
    public ResponseEntity<ClassResponseDTO> createClass(
            @RequestBody ClassRequestDTO request,
            Authentication authentication) {

        String teacherEmail = authentication.getName();

        ClassResponseDTO response = classService.createClass(request, teacherEmail);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}