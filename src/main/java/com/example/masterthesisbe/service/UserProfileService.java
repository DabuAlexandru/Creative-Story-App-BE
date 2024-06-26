package com.example.masterthesisbe.service;

import com.example.masterthesisbe.dto.userProfile.*;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.helpers.mappers.UserProfileMapper;
import com.example.masterthesisbe.model.*;
import com.example.masterthesisbe.repository.*;
import com.example.masterthesisbe.specification.UserProfileSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final StoryRepository storyRepository;
    private final StoryFavoriteRepository storyFavoriteRepository;
    private final StoryReadLaterRepository storyReadLaterRepository;
    private final FileInstanceRepository fileInstanceRepository;

    private final UserProfileMapper userProfileMapper;
    private final AuthenticationService authService;
    private final FileInstanceService fileInstanceService;

    public UserProfileResponseDto getProfileOfUser() {
        UserProfile userProfile = authService.getCurrentUserProfile();
        Set<StoryFavoriteResponseDto> favorites = storyFavoriteRepository.findByUserProfileId(userProfile.getId()).stream()
                .map(f -> new StoryFavoriteResponseDto(f.getStory().getId(), f.getCreatedOn()))
                .collect(Collectors.toSet());

        Set<StoryReadLaterResponseDto> readLater = storyReadLaterRepository.findByUserProfileId(userProfile.getId()).stream()
                .map(f -> new StoryReadLaterResponseDto(f.getStory().getId(), f.getCreatedOn()))
                .collect(Collectors.toSet());

        UserProfileResponseDto response = userProfileMapper.convertToResponseDto(userProfile);
        response.setFavorites(favorites);
        response.setReadingLists(readLater);

        return response;
    }

    public List<MinimalReferenceResponseDto> getAllAuthors(String penName) {
        Specification<UserProfile> spec = UserProfileSpecification.isAuthor();
        if (penName != null && !penName.isEmpty()) {
            spec = spec.and(UserProfileSpecification.penNameContains(penName));
        }
        return userProfileRepository.findAll(spec).stream()
                .map(userProfile -> new MinimalReferenceResponseDto(userProfile.getId(), userProfile.getPenName()))
                .toList();
    }

    public UserProfileReducedResponseDto getReducedProfileOfUser() {
        UserProfile userProfile = authService.getCurrentUserProfile();
        return userProfileMapper.convertToReducedResponseDto(userProfile);
    }

    public UserProfileResponseDto updateUserProfile(UpdateUserProfileRequestDto updatedUserProfile) {
        UserProfile userProfile = authService.getCurrentUserProfile();
        userProfileMapper.updateUserProfileWithDto(userProfile, updatedUserProfile);
        return this.userProfileMapper.convertToResponseDto(userProfileRepository.save(userProfile));
    }

    public void addStoryToFavorites(int storyId) {
        UserProfile userProfile = authService.getCurrentUserProfile();
        if (storyFavoriteRepository.existsByUserProfileIdAndStoryId(userProfile.getId(), storyId)) {
           throw new ApiException("Story with id " + storyId + " already added!");
        }
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException("There is no story with id: " + storyId));

        StoryFavorite newFavorite = new StoryFavorite(story, userProfile);
        storyFavoriteRepository.save(newFavorite);
    }

    public void removeStoryFromFavorites(int storyId) {
        UserProfile userProfile = authService.getCurrentUserProfile();
        Long deletedCount = storyFavoriteRepository.deleteByUserProfileIdAndStoryId(userProfile.getId(), storyId);
        if (deletedCount == 0) {
            throw new ApiException("There is no story with id: " + storyId);
        }
    }

    public void addStoryToReadLater(int storyId) {
        UserProfile userProfile = authService.getCurrentUserProfile();
        if (storyReadLaterRepository.existsByUserProfileIdAndStoryId(userProfile.getId(), storyId)) {
            throw new ApiException("Story with id " + storyId + " already added!");
        }
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException("There is no story with id: " + storyId));

        StoryReadLater storyForReadLater = new StoryReadLater(story, userProfile);
        storyReadLaterRepository.save(storyForReadLater);
    }

    public void removeStoryFromReadLater(int storyId) {
        UserProfile userProfile = authService.getCurrentUserProfile();
        Long deletedCount = storyReadLaterRepository.deleteByUserProfileIdAndStoryId(userProfile.getId(), storyId);
        if (deletedCount == 0) {
            throw new ApiException("There is no story with id: " + storyId);
        }
    }

    public void uploadProfilePicture(MultipartFile picture) {
        UserProfile userProfile = authService.getCurrentUserProfile();
        // at the moment for testing purposes I will only hold one picture at a time for a user
        FileInstance profilePicture = userProfile.getProfilePicture();
        if (profilePicture != null) {
            userProfile.setProfilePicture(null);
            fileInstanceService.deleteFile(profilePicture.getFileName());
            fileInstanceRepository.delete(profilePicture);
        }

        FileInstance newProfilePicture = fileInstanceService.userUploadFile(picture);
        userProfile.setProfilePicture(newProfilePicture);
        userProfileRepository.save(userProfile);
    }

    public void deleteProfilePicture() {
        UserProfile userProfile = authService.getCurrentUserProfile();
        FileInstance profilePicture = userProfile.getProfilePicture();
        if(userProfile.getProfilePicture() == null) {
            throw new ApiException("There is no saved profile picture for the current user!");
        }
        userProfile.setProfilePicture(null);
        fileInstanceService.deleteFile(profilePicture.getFileName());
        fileInstanceRepository.delete(profilePicture);
        userProfileRepository.save(userProfile);
    }
}
