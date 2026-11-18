package com.example.scrs.service;

import com.example.scrs.dto.LoginRequest;
import com.example.scrs.dto.RegisterRequest;
import com.example.scrs.dto.UserResponse;
import com.example.scrs.entity.User;
import com.example.scrs.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }
        User user = new User(request.fullName(), request.email(),
                passwordEncoder.encode(request.password()));
        user = userRepository.save(user);
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail());
    }

    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail());
    }
}
