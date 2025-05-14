package com.example.demo3.Controller;

import com.example.demo3.DTO.Authen.AuthenticationRequest;
import com.example.demo3.DTO.Authen.AuthenticationResponse;
import com.example.demo3.DTO.UserDTO.UserRequest;
import com.example.demo3.Entity.User;
import com.example.demo3.Entity.VerificationToken;
import com.example.demo3.Sevice.EmailService;
import com.example.demo3.Sevice.JwtService;
import com.example.demo3.repository.TokenRepo;
import com.example.demo3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;


    private final TokenRepo tokenRepo;



    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            // 1. Xác thực tài khoản và mật khẩu
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()
                    )
            );

            // 2. Tìm user trong DB
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng"));

            // 3. Kiểm tra nếu chưa xác thực email
            if (!user.isEnabled()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new AuthenticationResponse("Tài khoản chưa được xác thực qua email"));
            }

            // 4. Nếu OK → cấp token
            String token = jwtService.generateToken(user.getUsername());
            return ResponseEntity.ok(new AuthenticationResponse(token));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse("Sai tên đăng nhập hoặc mật khẩu"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponse("Đã xảy ra lỗi khi đăng nhập"));
        }
    }








    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        // 1. Lưu người dùng
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setEnabled(false);
        userRepository.save(user);

        // 2. Tạo token xác thực
        String token = jwtService.generateToken(user.getUsername());

        // 3. Gửi mail
        String verifyLink = "http://localhost:8080/verify?token=" + token;
        emailService.sendVerificationEmail(user.getEmail(), verifyLink);

        return ResponseEntity.ok("Vui lòng kiểm tra email để xác thực tài khoản.");
    }


    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        String username = jwtService.extractUsername(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token không hợp lệ");
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
        }

        User user = optionalUser.get();
        user.setEnabled(true);
        userRepository.save(user);

        return ResponseEntity.ok("Tài khoản đã được xác thực thành công!");
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<String> resendVerification(@RequestBody Map<String, String> body) {
        String username = body.get("username");

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy người dùng");
        }

        User user = optionalUser.get();
        if (user.isEnabled()) {
            return ResponseEntity.ok("Tài khoản đã được xác thực trước đó");
        }

        String token = jwtService.generateToken(user.getUsername());
        String verificationLink = "http://localhost:8080/verify?token=" + token;

        emailService.sendVerificationEmail(user.getEmail(), verificationLink);

        return ResponseEntity.ok("Đã gửi lại email xác thực. Vui lòng kiểm tra hộp thư đến.");
    }




}
