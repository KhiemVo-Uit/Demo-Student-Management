package com.example.demo3.DTO.UserDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserDTO {
     Long id;
     String username;
     String password;
      String email;
     String role;


}

