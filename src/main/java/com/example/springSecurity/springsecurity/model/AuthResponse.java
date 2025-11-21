package com.example.springSecurity.springsecurity.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String username;
    private String role;
    private String token;
}
