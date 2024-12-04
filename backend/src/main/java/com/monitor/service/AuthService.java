package com.monitor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    private String currentUsername;
    private String currentPassword;

    public void setCredentials(String username, String password) {
        this.currentUsername = username;
        this.currentPassword = password;
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
} 