package com.example.demo3.Mapping;

import com.example.demo3.DTO.UserDTO.UserRepone;
import com.example.demo3.DTO.UserDTO.UserRequest;
import com.example.demo3.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {


    //@Mapping(source = "role", target = "role")
    User toUser(UserRequest userRequest);


    UserRepone toUserRepone(User user);

    @Mapping(target = "id", ignore = true)
    void updateUserFromRequest(UserRequest userRequest,@MappingTarget User user);
}
