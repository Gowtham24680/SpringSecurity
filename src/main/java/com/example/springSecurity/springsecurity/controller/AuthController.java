package com.example.springSecurity.springsecurity.controller;

import com.example.springSecurity.springsecurity.model.AuthResponse;
import com.example.springSecurity.springsecurity.model.LoginRequest;
import com.example.springSecurity.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/auth/encode")
    public String encode() {
        return passwordEncoder.encode("1234");
    }
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        System.out.println("Received username = " + request.getUsername());
        System.out.println("Received password = " + request.getPassword());
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/auth/google")
    public RedirectView googleLogin() {
        return new RedirectView("/oauth2/authorization/google");
    }
    @GetMapping("/home")
    public String home() {
        return "Google Login Successful!";
    }


//    @PostMapping("/register")
//    public ResponseEntity<AuthResponse> register(@RequestBody RegistrationRequest request) {
//        return ResponseEntity.ok(userService.register(request));
//    }

}
