package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.UserProfileConstants;
import com.example.masterthesisbe.dto.story.UpdateUserProfileRequestDto;
import com.example.masterthesisbe.dto.story.UserProfileResponseDto;
import com.example.masterthesisbe.helpers.mappers.UserProfileMapper;
import com.example.masterthesisbe.model.User;
import com.example.masterthesisbe.model.UserProfile;
import com.example.masterthesisbe.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final AuthenticationService authService;

    public UserProfileResponseDto getProfileOfUser() {
        User loggedInUser = authService.getLoggedInUser();
        int userId = loggedInUser.getId();
        UserProfile foundUserProfile = this.userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(UserProfileConstants.USER_PROFILE_NOT_FOUND_MESSAGE));
        return userProfileMapper.convertToResponseDto(foundUserProfile);
    }

    public UserProfileResponseDto updateUserProfile(UpdateUserProfileRequestDto updatedUserProfile) {
        User loggedInUser = authService.getLoggedInUser();
        int userId = loggedInUser.getId();
        UserProfile foundUserProfile = this.userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(UserProfileConstants.NO_PERMISSIONS_TO_MODIFY));

        userProfileMapper.updateUserProfileWithDto(foundUserProfile, updatedUserProfile);
        return this.userProfileMapper.convertToResponseDto(userProfileRepository.save(foundUserProfile));
    }
}
