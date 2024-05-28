package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.fileInstance.FileInstanceResponseDto;
import com.example.masterthesisbe.dto.genre.GenreDto;
import com.example.masterthesisbe.dto.genre.GenreResponseDto;
import com.example.masterthesisbe.dto.story.*;
import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import com.example.masterthesisbe.dto.userProfile.UserProfileResponseDto;
import com.example.masterthesisbe.model.Genre;
import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.model.StoryOverallScore;
import com.example.masterthesisbe.model.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class StoryMapper {
    private final FileInstanceMapper fileInstanceMapper;
    private final UserProfileMapper userProfileMapper;
    private final GenreMapper genreMapper;

    public StoryResponseDto convertToResponseDto(Story story) {
        if (isNull(story)) {
            return null;
        }

        UserProfile author = story.getAuthor();
        UserProfileReferenceResponseDto convertedAuthor = userProfileMapper.convertToReferenceResponseDto(author);

        List<GenreResponseDto> convertedGenres = Optional.ofNullable(story.getGenres())
                .map(genres -> genres.stream()
                        .map(genreMapper::convertToResponseDto)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        List<GenreResponseDto> mainGenres = convertedGenres.stream()
                .filter(genre -> isNull(genre.getMainGenreId()))
                .toList();
        List<GenreResponseDto> subGenres = convertedGenres.stream()
                .filter(genre -> !isNull(genre.getMainGenreId()))
                .toList();

        StoryOverallScoreResponseDto overallScore = this.convertOverallScoreToResponseDto(story.getStoryOverallScore());

        var coverPicture = story.getCoverPicture();
        FileInstanceResponseDto convertedCoverPicture = coverPicture != null
                ? fileInstanceMapper.convertToResponseDto(coverPicture)
                : null;

        return new StoryResponseDto(
                story.getId(),
                story.getTitle(),
                story.getDescription(),
                overallScore,
                story.getPreview(),
                story.getCreatedOn(),
                story.getLastUpdatedOn(),
                convertedAuthor,
                mainGenres,
                subGenres,
                convertedCoverPicture
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

        List<GenreDto> subGenres = isNull(newStory.getSubGenres()) ? new ArrayList<>() : newStory.getSubGenres();
        List<GenreDto> storyGenres = newStory.getGenres();
        storyGenres.addAll(subGenres);

        return new Story(
                newStory.getTitle(),
                newStory.getDescription(),
                storyGenres.stream().map(genreMapper::convertFromDto).collect(Collectors.toList())
        );
    }

    public void updateStoryWithDto(Story story, UpdateStoryRequestDto storyDto) {
        story.setTitle(storyDto.getTitle());
        story.setDescription(storyDto.getDescription());
        story.setPreview(storyDto.getPreview());

        List<GenreDto> subGenres = isNull(storyDto.getSubGenres()) ? new ArrayList<>() : storyDto.getSubGenres();
        List<GenreDto> storyGenres = storyDto.getGenres();
        storyGenres.addAll(subGenres);

        story.setGenres(storyGenres.stream().map(genreMapper::convertFromDto).collect(Collectors.toList()));
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
