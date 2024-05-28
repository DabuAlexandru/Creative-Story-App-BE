package com.example.masterthesisbe.service;

import com.example.masterthesisbe.config.JwtService;
import com.example.masterthesisbe.constants.UserProfileConstants;
import com.example.masterthesisbe.dto.auth.AuthenticationRequest;
import com.example.masterthesisbe.dto.auth.AuthenticationResponse;
import com.example.masterthesisbe.dto.auth.RegisterRequest;
import com.example.masterthesisbe.dto.auth.UserDto;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.exception.IncorrectCredentials;
import com.example.masterthesisbe.helpers.mappers.UserMapper;
import com.example.masterthesisbe.model.Role;
import com.example.masterthesisbe.model.User;
import com.example.masterthesisbe.model.UserProfile;
import com.example.masterthesisbe.repository.UserProfileRepository;
import com.example.masterthesisbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final UserProfileRepository userProfileRepository;

    private void createUserProfile(User user, String penName) {
        UserProfile newUserProfile = new UserProfile();
        newUserProfile.setUser(user);
        newUserProfile.setPenName(penName);
        userProfileRepository.save(newUserProfile);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        this.createUserProfile(user, request.getPenName());

        UserDto userResponse = userMapper.convertToResponseDto(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .user(userResponse)
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(IncorrectCredentials::new);
        UserDto userResponse = userMapper.convertToResponseDto(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(userResponse)
                .build();
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName(); // Assuming email is used as the principal
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiException("User not found"));
        } else {
            throw new ApiException("User not authenticated"); // Handle appropriately
        }
    }

    public UserProfile getCurrentUserProfile() {
        User loggedInUser = this.getLoggedInUser();
        int userId = loggedInUser.getId();
        return this.userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(UserProfileConstants.USER_PROFILE_NOT_FOUND_MESSAGE));
    }
}
