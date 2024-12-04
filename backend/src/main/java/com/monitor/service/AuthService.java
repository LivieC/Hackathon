package com.monitor.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    @Getter
    private String token;

    public void setToken(String token) {
        this.token = token;
        log.info("OpenShift token updated");
    }

    public boolean hasToken() {
        return token != null && !token.isEmpty();
    }
} 