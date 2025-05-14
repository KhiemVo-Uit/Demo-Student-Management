package com.example.demo3.DTO.StudentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequest {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private Long userId;
    private Set<Long> subjectIds;
}
