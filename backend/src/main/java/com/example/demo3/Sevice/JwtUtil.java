package com.example.demo3.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final long jwtExpirationMs = 86400000; // 1 ngày

    // Lấy username từ token
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // Lấy tất cả claims (payload) từ token
    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired");
            throw e;
        } catch (JwtException e) {
            System.out.println("Invalid token");
            throw e;
        }
    }

    // Kiểm tra token hết hạn chưa
    public boolean isTokenExpired(String token) {
        final Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }

    // Validate token so với userDetails
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Tạo token từ username (bạn có thể thêm roles hoặc claims khác)
    public String generateToken(UserDetails userDetails) {
        // Lấy username
        String username = userDetails.getUsername();

        // Lấy role đầu tiên (nếu muốn lưu role vào token)
//        String role = "";
//        if (!userDetails.getAuthorities().isEmpty()) {
//            role = userDetails.getAuthorities().iterator().next().getAuthority();
//        }

        return Jwts.builder()
                .setSubject(username)                      // subject là username
              //  .claim("role", role)                       // thêm role vào payload (có thể bỏ nếu không cần)
                .setIssuedAt(new Date())                   // thời gian tạo token
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))  // thời gian hết hạn
                .signWith(SignatureAlgorithm.HS512, key) // ký token với secret và thuật toán HS512
                .compact();
    }

}
