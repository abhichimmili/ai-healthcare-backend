package com.spam.ai_healthcare_backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.spam.ai_healthcare_backend.dto.AuthResponse;
import com.spam.ai_healthcare_backend.dto.LoginRequest;
import com.spam.ai_healthcare_backend.model.User;
import com.spam.ai_healthcare_backend.repository.UserRepository;
import com.spam.ai_healthcare_backend.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {

        // Authenticate user using email and password
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        // Get authenticated user details
        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        // Fetch user from database
        User user = userRepository.findByEmail(
                userDetails.getUsername()
        );

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Generate JWT token
        String token = jwtService.generateToken(userDetails);

        // Return authentication response
        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole()
        );
    }
}