package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.userProfile.*;
import com.example.masterthesisbe.model.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserProfileMapper {

    public UserProfileResponseDto convertToResponseDto(UserProfile userProfile) {
        if (isNull(userProfile)) {
            return null;
        }

        Set<StoryFavoriteResponseDto> convertedFavorites = userProfile.getFavorites().stream()
                .map(s -> new StoryFavoriteResponseDto(
                        s.getStory().getId(),
                        s.getCreatedOn()
                )).collect(Collectors.toSet());

        Set<StoryReadLaterResponseDto> convertedReadingLists = userProfile.getReadingLists().stream()
                .map(s -> new StoryReadLaterResponseDto(
                        s.getStory().getId(),
                        s.getCreatedOn()
                )).collect(Collectors.toSet());

        return new UserProfileResponseDto(
                userProfile.getFullName(),
                userProfile.getBio(),
                userProfile.getLocation(),
                userProfile.getWebsite(),
                convertedFavorites,
                convertedReadingLists
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
                userProfile.getFullName(),
                userProfile.getBio(),
                userProfile.getLocation(),
                userProfile.getWebsite(),
                convertedFavoritesIds,
                convertedReadingListsIds
        );
    }

    public void updateUserProfileWithDto(UserProfile userProfile, UpdateUserProfileRequestDto userProfileDto) {
        userProfile.setFullName(userProfileDto.getFullName());
        userProfile.setBio(userProfileDto.getBio());
        userProfile.setLocation(userProfileDto.getLocation());
        userProfile.setWebsite(userProfile.getWebsite());
    }
}
