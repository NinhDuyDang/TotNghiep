package com.example.doan.service.imp;

import com.example.doan.entity.ConfirmationToken;
import com.example.doan.repository.ConfirmTokenRepository;
import com.example.doan.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenImp implements ConfirmationTokenService {
    @Autowired
    private ConfirmTokenRepository confirmTokenRepository;
    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmTokenRepository.save(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmTokenRepository.findByToken(token);
    }

    @Override
    public void setConfirmedAt(String token) {
        confirmTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
