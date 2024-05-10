package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.genre.GenreResponseDto;
import com.example.masterthesisbe.dto.story.*;
import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import com.example.masterthesisbe.dto.userProfile.UserProfileResponseDto;
import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.model.StoryOverallScore;
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
public class StoryMapper {
    private final UserProfileMapper userProfileMapper;
    private final GenreMapper genreMapper;

    public StoryResponseDto convertToResponseDto(Story story) {
        if (isNull(story)) {
            return null;
        }

        UserProfile author = story.getAuthor();
        UserProfileReferenceResponseDto convertedAuthor = userProfileMapper.convertToReferenceResponseDto(author);

        Set<GenreResponseDto> convertedGenres = Optional.ofNullable(story.getGenres())
                .map(genres -> genres.stream()
                        .map(genreMapper::convertToResponseDto)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        StoryOverallScoreResponseDto overallScore = this.convertOverallScoreToResponseDto(story.getStoryOverallScore());

        return new StoryResponseDto(
                story.getId(),
                story.getTitle(),
                story.getDescription(),
                overallScore,
                story.getPreview(),
                story.getCreatedOn(),
                story.getLastUpdatedOn(),
                convertedGenres,

                convertedAuthor
        );
    }

    public StoryContentResponseDto convertToContentResponseDto(Story story) {
        if (isNull(story)) {
            return null;
        }

        UserProfile author = story.getAuthor();
        UserProfileReferenceResponseDto convertedAuthor = userProfileMapper.convertToReferenceResponseDto(author);

        return new StoryContentResponseDto(
                story.getId(),
                story.getTitle(),
                story.getPreview(),
                story.getCreatedOn(),
                story.getLastUpdatedOn(),
                convertedAuthor
        );
    }

    public Story convertFromCreateRequestDto(CreateStoryRequestDto newStory) {
        if (isNull(newStory)) {
            return null;
        }

        return new Story(
                newStory.getTitle(),
                newStory.getDescription()
        );
    }

    public void updateStoryWithDto(Story story, UpdateStoryRequestDto storyDto) {
        story.setTitle(storyDto.getTitle());
        story.setDescription(storyDto.getDescription());
    }

    private StoryOverallScoreResponseDto convertOverallScoreToResponseDto(StoryOverallScore storyScore) {
        if (storyScore == null) {
            return null;
        }
        return new StoryOverallScoreResponseDto(
                storyScore.getNumOfReviews(),
                storyScore.getCharacterScore(),
                storyScore.getConflictScore(),
                storyScore.getPlotScore(),
                storyScore.getSettingScore(),
                storyScore.getThemeScore()
        );
    }
}
