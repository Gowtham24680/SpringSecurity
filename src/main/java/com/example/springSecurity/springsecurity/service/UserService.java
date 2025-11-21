package com.example.springSecurity.springsecurity.service;

import com.example.springSecurity.springsecurity.model.AuthResponse;
import com.example.springSecurity.springsecurity.model.LoginRequest;
import com.example.springSecurity.springsecurity.security.CustomUserDetailsService;
import com.example.springSecurity.springsecurity.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthResponse login(LoginRequest request) {

        // authenticate
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // load user details
        var user = customUserDetailsService.loadUserByUsername(request.getUsername());

        // generate token
        String token = jwtService.generateToken(user.getUsername());

        return new AuthResponse(
                user.getUsername(),
                user.getAuthorities().stream().findFirst().get().getAuthority(),
                token
        );
    }
}