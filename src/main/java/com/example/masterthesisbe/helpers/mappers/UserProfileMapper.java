package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.story.UpdateUserProfileRequestDto;
import com.example.masterthesisbe.dto.story.UserProfileResponseDto;
import com.example.masterthesisbe.model.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserProfileMapper {

    public UserProfileResponseDto convertToResponseDto(UserProfile userProfile) {
        if (isNull(userProfile)) {
            return null;
        }

        return new UserProfileResponseDto(
                userProfile.getFullName(),
                userProfile.getBio(),
                userProfile.getLocation(),
                userProfile.getWebsite()
        );
    }

    public void updateUserProfileWithDto(UserProfile userProfile, UpdateUserProfileRequestDto userProfileDto) {
        userProfile.setFullName(userProfileDto.getFullName());
        userProfile.setBio(userProfileDto.getBio());
        userProfile.setLocation(userProfileDto.getLocation());
        userProfile.setWebsite(userProfile.getWebsite());
    }
}
