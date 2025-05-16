package com.example.demo3.repository;

import com.example.demo3.Entity.Student;
import com.example.demo3.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}

