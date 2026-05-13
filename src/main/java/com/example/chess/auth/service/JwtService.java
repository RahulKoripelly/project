package com.example.chess.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service public class JwtService {
    private static final String secret_KEY = "myverysecuresecretkeywithatleast32characters";
    SecretKey key= Keys.hmacShaKeyFor(secret_KEY.getBytes());
    public String generateAccessToken(String email){
        return generateToken(email, 1000*60*30);
    }

    public String generateRefreshToken(String email){
        return generateToken(email, 1000L*60*60*24*7);
    }
    public String generateToken(String email, long expiry){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String extractEmail(String token){
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
    public boolean isTokenValid(String token){
        try{
            extractEmail(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}