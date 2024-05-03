package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.fileInstance.FileInstanceResponseDto;
import com.example.masterthesisbe.dto.userProfile.*;
import com.example.masterthesisbe.model.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserProfileMapper {
    private final FileInstanceMapper fileInstanceMapper;

    public UserProfileResponseDto convertToResponseDto(UserProfile userProfile) {
        if (isNull(userProfile)) {
            return null;
        }

        var profilePicture = userProfile.getProfilePicture();
        FileInstanceResponseDto convertedProfilePicture = profilePicture != null
                ? fileInstanceMapper.convertToResponseDto(profilePicture)
                : null;

        return new UserProfileResponseDto(
                userProfile.getId(),
                userProfile.getPenName(),
                userProfile.getHeadline(),
                userProfile.getFullName(),
                userProfile.getBio(),
                userProfile.getLocation(),
                userProfile.getWebsite(),
                convertedProfilePicture
        );
    }

    public UserProfileReducedResponseDto convertToReducedResponseDto(UserProfile userProfile) {
        if (isNull(userProfile)) {
            return null;
        }

        Set<Integer> convertedFavoritesIds = userProfile.getFavorites().stream()
                .map(s -> s.getStory().getId())
                .collect(Collectors.toSet());

        Set<Integer> convertedReadingListsIds = userProfile.getReadingLists().stream()
                .map(s -> s.getStory().getId())
                .collect(Collectors.toSet());

        return new UserProfileReducedResponseDto(
                userProfile.getId(),
                userProfile.getPenName(),
                userProfile.getHeadline(),
                userProfile.getFullName(),
                userProfile.getBio(),
                userProfile.getLocation(),
                userProfile.getWebsite(),
                convertedFavoritesIds,
                convertedReadingListsIds
        );
    }

    public UserProfileReferenceResponseDto convertToReferenceResponseDto(UserProfile userProfile) {
        if (isNull(userProfile)) {
            return null;
        }

        var profilePicture = userProfile.getProfilePicture();
        FileInstanceResponseDto convertedProfilePicture = profilePicture != null
                ? fileInstanceMapper.convertToResponseDto(profilePicture)
                : null;

        return new UserProfileReferenceResponseDto(
                userProfile.getId(),
                userProfile.getPenName(),
                userProfile.getHeadline(),
                userProfile.getFullName(),
                userProfile.getBio(),
                convertedProfilePicture
        );
    }

    public void updateUserProfileWithDto(UserProfile userProfile, UpdateUserProfileRequestDto userProfileDto) {
        userProfile.setPenName(userProfileDto.getPenName());
        userProfile.setHeadline(userProfileDto.getHeadline());
        userProfile.setFullName(userProfileDto.getFullName());
        userProfile.setBio(userProfileDto.getBio());
        userProfile.setLocation(userProfileDto.getLocation());
        userProfile.setWebsite(userProfileDto.getWebsite());
    }
}
