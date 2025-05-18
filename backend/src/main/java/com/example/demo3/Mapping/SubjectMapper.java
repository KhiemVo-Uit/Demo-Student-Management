package com.example.demo3.Mapping;




import com.example.demo3.DTO.SubjectDTO;
import com.example.demo3.Entity.Student;
import com.example.demo3.Entity.Subject;
import com.example.demo3.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;



import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Nodes.collect;

public class SubjectMapper {

    // Entity -> DTO
    public static SubjectDTO toDTO(Subject subject) {
        SubjectDTO dto = new SubjectDTO();
        dto.setId(subject.getId());
        dto.setName(subject.getName());

        List<String> usernames = subject.getStudents()
                .stream()
                .map(student -> student.getUser().getUsername())
                .collect(Collectors.toList());

        dto.setStudentUsernames(usernames);
        return dto;
    }

}


