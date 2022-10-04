package com.example.doan.service;

import com.example.doan.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    void register(User user);
    String confirm(String token);
    Optional<User> findUserByEmail(String email);
    Integer updatePassword(String password, String email);
}
