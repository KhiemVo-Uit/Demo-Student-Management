package com.example.demo3.Mapping;


import com.example.demo3.DTO.SubjectDto.SubjectDTO;
import com.example.demo3.Entity.Subject;
import com.example.demo3.Entity.Student;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SubjectMapper {


    @Mapping(target = "studentIds", expression = "java(mapStudentsToIds(subject.getStudents()))")
    SubjectDTO toDto(Subject subject);

    @Mapping(target = "students", ignore = true) // ta sẽ set thủ công
    Subject toEntity(SubjectDTO subjectDTO);

    default Set<Long> mapStudentsToIds(Set<Student> students) {
        return students == null ? Set.of() :
                students.stream().map(Student::getId).collect(Collectors.toSet());
    }
}

