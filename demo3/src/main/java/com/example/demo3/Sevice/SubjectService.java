package com.example.demo3.Sevice;

import com.example.demo3.DTO.SubjectDto.SubjectDTO;
import com.example.demo3.Entity.Student;
import com.example.demo3.Entity.Subject;
import com.example.demo3.Mapping.SubjectMapper;
import com.example.demo3.repository.StudentRepository;
import com.example.demo3.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final SubjectMapper subjectMapper;

    public List<SubjectDTO> findAll() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toDto)
                .collect(Collectors.toList());
    }

    public SubjectDTO findById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found: " + id));
        return subjectMapper.toDto(subject);
    }

    public SubjectDTO save(SubjectDTO dto) {
        // 1. Chuyển DTO -> Entity
        Subject subject = subjectMapper.toEntity(dto);

        // 2. Lấy tập Student từ DB và gán hai chiều
        if (dto.getStudentIds() != null && !dto.getStudentIds().isEmpty()) {
            Set<Student> students = dto.getStudentIds().stream()
                    .map(id -> studentRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Student not found: " + id)))
                    .collect(Collectors.toSet());

            // Gán bên Subject (inverse side)
            subject.setStudents(students);

            // Gán ngược bên Student (owning side) để Hibernate biết phải persist vào bảng nối
            students.forEach(s -> {
                if (s.getSubjects() == null) {
                    s.setSubjects(new HashSet<>());
                }
                s.getSubjects().add(subject);
            });
        } else {
            subject.setStudents(Collections.emptySet());
        }

        // 3. Lưu Subject. Vì ta đã gán owning side, Hibernate sẽ tự động cập nhật bảng nối
        Subject saved = subjectRepository.save(subject);

        // 4. Trả về DTO
        return subjectMapper.toDto(saved);
    }


    public SubjectDTO update(Long id, SubjectDTO dto) {
        Subject existing = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found: " + id));

        existing.setName(dto.getName());

        if (dto.getStudentIds() != null) {
            Set<Student> students = dto.getStudentIds().stream()
                    .map(studentRepository::findById)
                    .map(opt -> opt.orElseThrow(() -> new RuntimeException("Student not found")))
                    .collect(Collectors.toSet());
            existing.setStudents(students);
        }

        return subjectMapper.toDto(subjectRepository.save(existing));
    }

    public void delete(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new RuntimeException("Subject not found: " + id);
        }
        subjectRepository.deleteById(id);
    }
}

