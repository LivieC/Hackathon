package com.monitor.controller;

import com.monitor.model.TokenRequest;
import com.monitor.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<String> setToken(@RequestBody TokenRequest request) {
        authService.setToken(request.token());
        return ResponseEntity.ok("Token updated successfully");
    }
}
