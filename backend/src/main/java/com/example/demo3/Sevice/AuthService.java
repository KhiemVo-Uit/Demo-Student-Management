package com.example.demo3.Sevice;

import com.example.demo3.SecurityConfig.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final com.example.demo3.Sevice.JwtUtil jwtUtils;

    public AuthService(AuthenticationManager authenticationManager, com.example.demo3.Sevice.JwtUtil jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return jwtUtils.generateToken(userDetails);
    }
}
