package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.dto.userProfile.UpdateUserProfileRequestDto;
import com.example.masterthesisbe.dto.userProfile.UserProfileReducedResponseDto;
import com.example.masterthesisbe.dto.userProfile.UserProfileResponseDto;
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

    @GetMapping("/reduced")
    public ResponseEntity<UserProfileReducedResponseDto> retrieveReducedUserProfile() {
        return ResponseEntity.ok().body(this.userProfileService.getReducedProfileOfUser());
    }

    @PutMapping("/update")
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(@Valid @RequestBody UpdateUserProfileRequestDto updatedUserProfile) {
        return ResponseEntity.ok().body(this.userProfileService.updateUserProfile(updatedUserProfile));
    }

    @PutMapping("/favorite/add/{storyId}")
    public ResponseEntity<Void> addStoryToFavorites(@PathVariable int storyId) {
        this.userProfileService.addStoryToFavorites(storyId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/favorite/remove/{storyId}")
    public ResponseEntity<Void> removeStoryFromFavorites(@PathVariable int storyId) {
        this.userProfileService.removeStoryFromFavorites(storyId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/readLater/add/{storyId}")
    public ResponseEntity<Void> addStoryToReadLater(@PathVariable int storyId) {
        this.userProfileService.addStoryToReadLater(storyId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/readLater/remove/{storyId}")
    public ResponseEntity<Void> removeStoryFromReadLater(@PathVariable int storyId) {
        this.userProfileService.removeStoryFromReadLater(storyId);
        return ResponseEntity.ok().build();
    }
}
