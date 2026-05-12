package com.example.chess.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service public class JwtService {
    private static final String secret_KEY = "myverysecuresecretkeywithatleast32characters";
    SecretKey key= Keys.hmacShaKeyFor(secret_KEY.getBytes());
    public String generateToken(String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
}