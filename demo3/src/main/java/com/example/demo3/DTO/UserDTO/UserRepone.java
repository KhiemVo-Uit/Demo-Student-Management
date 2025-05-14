package com.example.demo3.DTO.UserDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRepone {
    Long id;
    String username;
    //String password;
    String name;
    String role;
}
