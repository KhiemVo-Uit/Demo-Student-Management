package com.example.demo3.Sevice;

import com.example.demo3.DTO.StudentDTO.StudentDTO;
import com.example.demo3.DTO.UserDTO.UserDTO;
import com.example.demo3.Entity.Student;
import com.example.demo3.Entity.User;
import com.example.demo3.Mapping.StudentMapper;
import com.example.demo3.repository.StudentRepository;
import com.example.demo3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;


    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        return students.stream().map(student -> {
            StudentDTO dto = new StudentDTO();
            dto.setId(student.getId());
            dto.setFullName(student.getFullName());
            dto.setAddress(student.getAddress());
            dto.setEmail(student.getEmail());

            if (student.getUser() != null) {
                dto.setUsername(student.getUser().getUsername());
                dto.setUserId(student.getUser().getId());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public StudentDTO assignUser(Long studentId, Long userId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        student.setUser(user);
        studentRepository.save(student);
        return studentMapper.toDTO(student); // chuyển về StudentDTO
    }


    public Student createStudent(StudentDTO studentDTO) {
        Student student = Student.builder()
                .fullName(studentDTO.getFullName())
                .address(studentDTO.getAddress())
                .email(studentDTO.getEmail())
                .build();

        return studentRepository.save(student);
    }
}


