package com.example.demo3.Controller;

import com.example.demo3.DTO.StudentDTO.StudentDTO;
import com.example.demo3.Entity.Student;
import com.example.demo3.Entity.User;
import com.example.demo3.Mapping.StudentMapper;
import com.example.demo3.Sevice.StudentService;
import com.example.demo3.repository.StudentRepository;
import com.example.demo3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/students")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor

public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentMapper studentMapper;

    private final StudentService studentService;

    @PutMapping("/{id}/assign-user/{userId}")
    public ResponseEntity<StudentDTO> assignUserToStudent(@PathVariable Long id, @PathVariable Long userId) {
        StudentDTO updatedStudent = studentService.assignUser(id, userId);
        return ResponseEntity.ok(updatedStudent);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO) {
        Student savedStudent = studentService.createStudent(studentDTO);
        return ResponseEntity.ok(savedStudent);
    }




    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> studentDTOs = studentService.getAllStudents();
        return ResponseEntity.ok(studentDTOs);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

}
