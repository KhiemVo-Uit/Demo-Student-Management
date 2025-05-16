package com.example.demo3.Mapping;

import com.example.demo3.DTO.UserDTO.UserDTO;
import com.example.demo3.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);

}

