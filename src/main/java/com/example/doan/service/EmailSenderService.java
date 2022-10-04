package com.example.doan.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {
    void send(String to, String email);
}
