package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.constants.StoryConstants;
import com.example.masterthesisbe.model.Genre;
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
public class UpdateStoryRequestDto {
    @NotBlank(message = StoryConstants.TITLE_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String title;
    @NotBlank(message = StoryConstants.DESCRIPTION_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String description;
    @NotBlank(message = StoryConstants.PREVIEW_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String preview;
}
