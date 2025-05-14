package com.example.demo3.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String username;
    String password;
    String email;
    String name;
    String role;
    boolean enabled = false;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
     Student student;

//    @OneToOne(mappedBy = "verificationToken", cascade = CascadeType.ALL)
//    VerificationToken verificationToken;


}
