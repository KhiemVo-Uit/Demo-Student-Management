package com.example.demo3.Sevice;

import com.example.demo3.DTO.UserDTO.UserRepone;
import com.example.demo3.DTO.UserDTO.UserRequest;
import com.example.demo3.Entity.User;
import com.example.demo3.Entity.VerificationToken;
import com.example.demo3.Mapping.UserMapper;
import com.example.demo3.repository.TokenRepo;
import com.example.demo3.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private TokenRepo tokenRepository;
    @Autowired
    private EmailService emailService;





    private  UserRepository userRepository;
    private  UserMapper userMapper;
    private  PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // Lấy danh sách tất cả người dùng
    public List<UserRepone> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserRepone)
                .toList();
    }

    // Lấy người dùng theo ID
    public Optional<UserRepone> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toUserRepone);
    }

    // Tạo mới người dùng
    public UserRepone createUser(UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        // ✅ Mã hóa mật khẩu trước khi lưu
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);
        return userMapper.toUserRepone(savedUser);
    }

    // Cập nhật người dùng
    public Optional<UserRepone> updateUser(Long id, UserRequest userRequest) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userMapper.updateUserFromRequest(userRequest, userToUpdate);
            userRepository.save(userToUpdate);
            return Optional.of(userMapper.toUserRepone(userToUpdate));
        } else {
            throw new RuntimeException("Không tìm thấy người dùng với ID: " + id);
        }
    }



    // Xóa người dùng
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }




}
