package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.constants.StoryConstants;
import com.example.masterthesisbe.model.Genre;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoryRequestDto {
    @NotBlank(message = StoryConstants.TITLE_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String title;

    private String description;

    private Set<Long> genreIds;
}
