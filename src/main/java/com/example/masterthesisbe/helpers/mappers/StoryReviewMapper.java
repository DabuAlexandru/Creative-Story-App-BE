package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.auth.UserDto;
import com.example.masterthesisbe.dto.genre.GenreResponseDto;
import com.example.masterthesisbe.dto.story.CreateStoryRequestDto;
import com.example.masterthesisbe.dto.story.StoryContentResponseDto;
import com.example.masterthesisbe.dto.story.StoryResponseDto;
import com.example.masterthesisbe.dto.story.UpdateStoryRequestDto;
import com.example.masterthesisbe.dto.storyReview.AddStoryReviewRequestDto;
import com.example.masterthesisbe.dto.storyReview.StoryReviewResponseDto;
import com.example.masterthesisbe.dto.storyReview.StoryReviewWithVotesResponseDto;
import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import com.example.masterthesisbe.dto.userProfile.UserProfileResponseDto;
import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.model.StoryReview;
import com.example.masterthesisbe.model.User;
import com.example.masterthesisbe.model.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class StoryReviewMapper {
    private final UserProfileMapper userProfileMapper;
    public StoryReviewResponseDto convertToResponseDto(StoryReview storyReview) {
        if (isNull(storyReview)) {
            return null;
        }

        UserProfile userProfile = storyReview.getUserProfile();
        UserProfileReferenceResponseDto convertedUserProfile = userProfileMapper.convertToReferenceResponseDto(userProfile);

        return new StoryReviewResponseDto(
                storyReview.getId(),
                storyReview.getTitle(),
                storyReview.getContent(),
                storyReview.getCharacterScore(),
                storyReview.getConflictScore(),
                storyReview.getPlotScore(),
                storyReview.getSettingScore(),
                storyReview.getThemeScore(),
                storyReview.getCreatedOn(),
                convertedUserProfile
        );
    }

    public StoryReviewWithVotesResponseDto convertToVoteResponseDto(StoryReview storyReview) {
        if (isNull(storyReview)) {
            return null;
        }

        UserProfile userProfile = storyReview.getUserProfile();
        UserProfileReferenceResponseDto convertedUserProfile = userProfileMapper.convertToReferenceResponseDto(userProfile);

        return new StoryReviewWithVotesResponseDto(
                storyReview.getId(),
                storyReview.getTitle(),
                storyReview.getContent(),
                storyReview.getCharacterScore(),
                storyReview.getConflictScore(),
                storyReview.getPlotScore(),
                storyReview.getSettingScore(),
                storyReview.getThemeScore(),
                storyReview.getCreatedOn(),
                convertedUserProfile,
                0,
                (byte) 0
        );
    }

    public StoryReview convertFromCreateRequestDto(AddStoryReviewRequestDto newStoryReview) {
        if (isNull(newStoryReview)) {
            return null;
        }

        int completionPercentage = 100;
        return new StoryReview(
                newStoryReview.getTitle(),
                newStoryReview.getContent(),
                completionPercentage,
                newStoryReview.getCharacterScore(),
                newStoryReview.getConflictScore(),
                newStoryReview.getPlotScore(),
                newStoryReview.getSettingScore(),
                newStoryReview.getThemeScore()
        );
    }
}
