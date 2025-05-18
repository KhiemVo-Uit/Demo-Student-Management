package com.example.demo3.Mapping;

import com.example.demo3.DTO.StudentDTO.StudentDTO;
import com.example.demo3.Entity.Student;
import com.example.demo3.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toEntity(StudentDTO dto) {
        if (dto == null) {
            return null;
        }

        Student student = new Student();
        student.setId(dto.getId());
        student.setFullName(dto.getFullName());
        student.setEmail(dto.getEmail());

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            student.setUser(user);
        } else {
            student.setUser(null);
        }

        return student;
    }

    public StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }

        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFullName(student.getFullName());

        if (student.getUser() != null) {
            dto.setUserId(student.getUser().getId());
        } else {
            dto.setUserId(null);
        }

        return dto;
    }
}
