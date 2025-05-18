package com.example.demo3.repository;

import com.example.demo3.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s JOIN s.students st WHERE st.username = :username")
    List<Subject> findByStudentUsername(@Param("username") String username);
}

