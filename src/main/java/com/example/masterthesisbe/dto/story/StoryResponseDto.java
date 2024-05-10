package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.dto.genre.GenreResponseDto;
import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import com.example.masterthesisbe.dto.userProfile.UserProfileResponseDto;
import com.example.masterthesisbe.model.StoryOverallScore;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponseDto {
    private long id;
    private String title;
    private String description;
    private StoryOverallScoreResponseDto storyOverallScore;

    private String preview;

    private Instant createdOn;
    private Instant lastUpdatedOn;
    private Set<GenreResponseDto> genres;
    private UserProfileReferenceResponseDto author;
}
