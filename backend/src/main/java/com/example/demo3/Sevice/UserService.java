package com.example.demo3.Sevice;

import com.example.demo3.DTO.UserDTO.AuthResponse;
import com.example.demo3.DTO.UserDTO.LoginDTO;
import com.example.demo3.DTO.UserDTO.UserDTO;
import com.example.demo3.Entity.User;

import java.util.List;

public interface UserService {
    AuthResponse login(LoginDTO loginDTO);
    UserDTO register(User user);

    List<User> getAllUsers();

}