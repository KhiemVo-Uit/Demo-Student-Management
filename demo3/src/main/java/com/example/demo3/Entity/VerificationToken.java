package com.example.demo3.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerificationToken {
    @Id
    @GeneratedValue
    private Long id;

    private String token;  // Mã token xác nhận

    @OneToOne
    @JoinColumn(name = "user_id")  // Liên kết với bảng User thông qua khóa ngoại user_id
    private User user;     // Liên kết với bảng User

    private LocalDateTime expiryDate;  // Ngày hết hạn của token
}

