package com.example.demo3.Sevice;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        String base64Key = Encoders.BASE64.encode(keyBytes);
        System.out.println("Base64 secret key:");
        System.out.println(base64Key);
    }
}

