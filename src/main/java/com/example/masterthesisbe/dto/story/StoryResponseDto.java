package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.dto.fileInstance.FileInstanceResponseDto;
import com.example.masterthesisbe.dto.genre.GenreResponseDto;
import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import lombok.*;

import java.time.Instant;
import java.util.List;

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
    private UserProfileReferenceResponseDto author;

    private List<GenreResponseDto> genres;
    private List<GenreResponseDto> subGenres;

    private FileInstanceResponseDto coverPicture;
}
