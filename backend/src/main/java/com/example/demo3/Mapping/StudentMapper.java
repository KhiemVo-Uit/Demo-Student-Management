package com.example.demo3.Mapping;

import com.example.demo3.DTO.StudentDTO.StudentDTO;
import com.example.demo3.Entity.Student;
import com.example.demo3.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "user", source = "userId", qualifiedByName = "userIdToUser")
    Student toEntity(StudentDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    StudentDTO toDTO(Student student);

    @Named("userIdToUser")
    default User userIdToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        return user;
    }
}
