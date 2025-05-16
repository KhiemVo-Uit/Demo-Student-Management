package com.example.demo3.DTO.StudentDTO;

import com.example.demo3.DTO.UserDTO.UserDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String fullName;
    private String address;
    private String email;

    private String username; // nếu muốn trả về username

    private Long userId;
}

