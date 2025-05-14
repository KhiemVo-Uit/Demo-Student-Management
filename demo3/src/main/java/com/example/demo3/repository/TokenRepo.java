package com.example.demo3.repository;

import com.example.demo3.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<VerificationToken, Long> {
    public VerificationToken findByToken(String token);

    public VerificationToken findByUserId(Long id);

}
