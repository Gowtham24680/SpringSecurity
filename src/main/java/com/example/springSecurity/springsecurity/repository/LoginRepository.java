package com.example.springSecurity.springsecurity.repository;

import com.example.springSecurity.springsecurity.entity.LoginRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<LoginRequestEntity, Long> {

    Optional<LoginRequestEntity> findByUsername(String username);
}
