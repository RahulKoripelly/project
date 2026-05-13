package com.example.chess.auth.controller;

import com.example.chess.auth.dto.AuthResponse;
import com.example.chess.auth.dto.LoginRequest;
import com.example.chess.auth.dto.RefreshTokenRequest;
import com.example.chess.auth.dto.RegisterRequest;
import com.example.chess.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    private String regsiter(@Valid@RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @PostMapping("/login")
    private AuthResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request){
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        return authService.logout(token);
    }
}
