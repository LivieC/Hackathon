package com.monitor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private String currentUsername;
    private String currentPassword;
    private final PasswordService passwordService;

    public void setCredentials(String username, String hashedPassword) {
        this.currentUsername = username;
        this.currentPassword = hashedPassword;
        log.info("Credentials updated for user: {}", username);
    }

    public String getUsername() {
        return currentUsername;
    }

    public String getPassword() {
        return currentPassword;
    }

    public boolean hasCredentials() {
        return currentUsername != null && currentPassword != null;
    }

    public boolean verifyCredentials(String username, String hashedPassword) {
        return username.equals(currentUsername) && 
               hashedPassword.equals(currentPassword);
    }
} 