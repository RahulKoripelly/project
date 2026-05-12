package com.example.chess.auth.service;

import com.example.chess.auth.dto.AuthResponse;
import com.example.chess.auth.dto.LoginRequest;
import com.example.chess.auth.dto.RegisterRequest;
import com.example.chess.user.entity.User;
import com.example.chess.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        if(userRepository.existsByUsername((request.getUsername()))){
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
         return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email or Password"));

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!passwordMatches){
            throw new RuntimeException("Invalid Email or Password");
        }
        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

}
