package com.example.masterthesisbe.dto.auth;

import com.example.masterthesisbe.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    UserDto user;
    private String token;
}
