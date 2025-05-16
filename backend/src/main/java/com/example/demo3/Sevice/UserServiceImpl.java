package com.example.demo3.Service;

import com.example.demo3.DTO.UserDTO.AuthResponse;
import com.example.demo3.DTO.UserDTO.LoginDTO;
import com.example.demo3.DTO.UserDTO.UserDTO;
import com.example.demo3.Entity.User;
import com.example.demo3.Mapping.UserMapper;
import com.example.demo3.Sevice.UserService;
import com.example.demo3.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customUserDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private com.example.demo3.Service.JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public AuthResponse login(LoginDTO dto) {
        User user = userRepo.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // ✅ Tạo UserDetails thủ công từ user
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );

        // ✅ Truyền đúng đối tượng userDetails vào hàm generateToken
        String token = jwtUtil.generateToken(userDetails);


        String role = "";
        if (!userDetails.getAuthorities().isEmpty()) {
            role = userDetails.getAuthorities().iterator().next().getAuthority();
        }
        return new AuthResponse(token,role, userMapper.toDTO(user));
    }


    @Override
    public UserDTO register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));

        // Chuẩn hóa role: thêm "ROLE_" nếu chưa có, và chuyển thành uppercase
        if (!user.getRole().toUpperCase().startsWith("ROLE_")) {
            user.setRole("ROLE_" + user.getRole().toUpperCase());
        } else {
            // Nếu có ROLE_ rồi, chuẩn hóa uppercase
            user.setRole(user.getRole().toUpperCase());
        }

        return userMapper.toDTO(userRepo.save(user));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }


}
