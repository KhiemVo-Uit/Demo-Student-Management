package com.example.demo3.Sevice;


import com.example.demo3.DTO.StudentDTO.StudentRequest;
import com.example.demo3.DTO.StudentDTO.StudentResponse;
import com.example.demo3.DTO.SubjectDto.SubjectDTO;
import com.example.demo3.Entity.Student;
import com.example.demo3.Entity.Subject;
import com.example.demo3.Entity.User;
import com.example.demo3.Mapping.StudentMapper;

import com.example.demo3.Mapping.SubjectMapper;
import com.example.demo3.repository.StudentRepository;
import com.example.demo3.repository.SubjectRepository;
import com.example.demo3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private  StudentRepository studentRepository;
    private  UserRepository userRepository;
    private  StudentMapper studentMapper;
    private  SubjectMapper subjectMapper;
    private SubjectRepository subjectRepository;



    @Autowired
    public StudentService(StudentRepository studentRepository, UserRepository userRepository,
                          StudentMapper studentMapper, SubjectMapper subjectMapper,SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.studentMapper = studentMapper;
        this.subjectMapper = subjectMapper;
        this.subjectRepository = subjectRepository;
    }

    public StudentResponse save(StudentRequest request) {
        // 1. Ánh xạ DTO -> Entity (không gán user)
        Student student = studentMapper.toStudent(request);

        // 2. Nếu có userId, gán user vào student
        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found: " + request.getUserId()));
            student.setUser(user);
        } else {
            student.setUser(null);
        }

        // 3. Xử lý subjects như trước
        if (request.getSubjectIds() != null && !request.getSubjectIds().isEmpty()) {
            Set<Subject> subjects = request.getSubjectIds().stream()
                    .map(id -> subjectRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Subject not found: " + id)))
                    .collect(Collectors.toSet());
            student.setSubjects(subjects);
            subjects.forEach(sub -> {
                if (sub.getStudents() == null) sub.setStudents(new HashSet<>());
                sub.getStudents().add(student);
            });
        } else {
            student.setSubjects(Collections.emptySet());
        }

        // 4. Lưu student
        Student saved = studentRepository.save(student);

        // 5. Trả về DTO
        return studentMapper.toStudentResponse(saved);
    }

    public StudentResponse createStudent(StudentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User!"));
        Student student = studentMapper.toStudent(request, user);
        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toStudentResponse)
                .toList();
    }

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy student!"));
        return studentMapper.toStudentResponse(student);
    }

    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy student!"));
        studentMapper.updateStudentFromRequest(request, student);
        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy student!");
        }
        studentRepository.deleteById(id);
    }
}
