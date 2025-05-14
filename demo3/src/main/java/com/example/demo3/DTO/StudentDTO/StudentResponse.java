package com.example.demo3.DTO.StudentDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String username; // Trả về username của User nếu cần
}

