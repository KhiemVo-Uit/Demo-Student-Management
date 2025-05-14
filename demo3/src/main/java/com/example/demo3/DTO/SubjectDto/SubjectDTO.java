package com.example.demo3.DTO.SubjectDto;

import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDTO {
    private Long id;
    private String name;

    private Set<Long> studentIds;
}

