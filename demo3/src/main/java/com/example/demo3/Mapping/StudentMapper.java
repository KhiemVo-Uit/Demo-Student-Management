package com.example.demo3.Mapping;

import com.example.demo3.DTO.StudentDTO.StudentRequest;
import com.example.demo3.DTO.StudentDTO.StudentResponse;
import com.example.demo3.Entity.Student;
import com.example.demo3.Entity.Subject;
import com.example.demo3.Entity.User;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", expression = "java(user)") // id do DB tự sinh
    Student toStudent(StudentRequest request, @Context User user);

    Student toStudent(StudentRequest request);

    @Mapping(target = "username", expression = "java(student.getUser() != null ? student.getUser().getUsername() : null)")
    StudentResponse toStudentResponse(Student student);

 default Set<Long> mapSubjectsToIds(Set<Subject> subjects) {
  return subjects == null ? Set.of() :
          subjects.stream().map(Subject::getId).collect(Collectors.toSet());
 }


    // Cập nhật thông tin student từ request vào entity hiện có
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromRequest(StudentRequest request, @MappingTarget Student student);
}
