package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.constants.StoryConstants;
import com.example.masterthesisbe.dto.genre.GenreDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoryRequestDto {
    @NotBlank(message = StoryConstants.TITLE_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String title;
    @NotBlank(message = StoryConstants.DESCRIPTION_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String description;
    @NotBlank(message = StoryConstants.PREVIEW_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String preview;

    @NotNull(message = StoryConstants.GENRES_NOT_NULL_CONSTRAINT_MESSAGE)
    @NotEmpty(message = StoryConstants.NON_EMPTY_MAIN_GENRE_LIST)
    private List<GenreDto> genres;
    private List<GenreDto> subGenres;
}
