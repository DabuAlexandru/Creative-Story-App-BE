package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.constants.StoryConstants;
import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStoryRequestDto {
    @NotBlank(message = StoryConstants.TITLE_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String title;

    private String description;
}
