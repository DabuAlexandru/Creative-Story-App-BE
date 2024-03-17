package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.dto.auth.AuthenticationRequest;
import com.example.masterthesisbe.dto.auth.AuthenticationResponse;
import com.example.masterthesisbe.dto.auth.RegisterRequest;
import com.example.masterthesisbe.dto.auth.UserDto;
import com.example.masterthesisbe.helpers.mappers.UserMapper;
import com.example.masterthesisbe.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}
