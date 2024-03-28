package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.dto.story.UpdateUserProfileRequestDto;
import com.example.masterthesisbe.dto.story.UserProfileResponseDto;
import com.example.masterthesisbe.helpers.handlers.ValidationHandler;
import com.example.masterthesisbe.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/userProfile")
@RequiredArgsConstructor
public class UserProfileController extends ValidationHandler {
    private final UserProfileService userProfileService;

    @GetMapping()
    public ResponseEntity<UserProfileResponseDto> retrieveUserProfile() {
        return ResponseEntity.ok().body(this.userProfileService.getProfileOfUser());
    }

    @PutMapping("/update")
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(@Valid @RequestBody UpdateUserProfileRequestDto updatedUserProfile) {
        return ResponseEntity.ok().body(this.userProfileService.updateUserProfile(updatedUserProfile));
    }
}
