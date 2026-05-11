package com.example.chess.auth.controller;

import com.example.chess.auth.dto.RegisterRequest;
import com.example.chess.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    private String regsiter(@Valid@RequestBody RegisterRequest request){
        return authService.register(request);
    }
}
