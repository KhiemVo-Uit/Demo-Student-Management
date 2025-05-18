package com.example.demo3.Sevice;

import com.example.demo3.DTO.SubjectDTO;
import com.example.demo3.Entity.Subject;
import com.example.demo3.Mapping.SubjectMapper;
import com.example.demo3.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectDTO> getSubjectsByStudentUsername(String username) {
        List<Subject> subjects = subjectRepository.findByStudentUsername(username);
        return subjects.stream()
                .map(SubjectMapper::toDTO)
                .collect(Collectors.toList());
    }
}
