package com.example.springSecurity.springsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthProvider customAuthProvider;
    private final JwtAuthenticationFilter jwtFilter;
    private final CustomOAuth2SuccessHandler oAuth2SuccessHandler;

    public SecurityConfig(CustomAuthProvider customAuthProvider,
                          JwtAuthenticationFilter jwtFilter,
                          CustomOAuth2SuccessHandler oAuth2SuccessHandler) {

        this.customAuthProvider = customAuthProvider;
        this.jwtFilter = jwtFilter;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.authenticationProvider(customAuthProvider);

        return builder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/auth/login",
                        "/auth/encode",
                        "/auth/google",
                        "/oauth2/**",
                        "/public/**"
                ).permitAll()
                .anyRequest().authenticated()
        );

        // GOOGLE LOGIN ACTIVATION (correct)
        http.oauth2Login(oauth -> oauth
                .loginPage("/oauth2/authorization/google")
                .successHandler(oAuth2SuccessHandler)
        );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
