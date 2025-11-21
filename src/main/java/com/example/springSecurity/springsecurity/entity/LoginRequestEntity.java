package com.example.springSecurity.springsecurity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String username;


    @Column(nullable = false)
    private String password; // stored encoded


    @Column(nullable = false)
    private String role; // e.g. ROLE_USER, ROLE_ADMIN


    @Column(nullable = false)
    private boolean enabled = true;
}

