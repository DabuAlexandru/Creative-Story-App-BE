package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.constants.StoryConstants;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStoryRequestDto {
    @NotBlank(message = StoryConstants.TITLE_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String title;
    private String description;
    @NotNull(message = StoryConstants.GENRES_NOT_NULL_CONSTRAINT_MESSAGE)
    private Set<Integer> genreIds;
}
