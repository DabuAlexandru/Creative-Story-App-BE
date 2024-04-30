package com.example.masterthesisbe.dto.storyReview;

import com.example.masterthesisbe.constants.StoryReviewConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddStoryReviewRequestDto {
    @NotBlank(message = StoryReviewConstants.TITLE_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String title;

    @NotBlank(message = StoryReviewConstants.CONTENT_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String content;

    @NotNull(message = StoryReviewConstants.SCORE_MISSING_MESSAGE)
    private int characterScore;

    @NotNull(message = StoryReviewConstants.SCORE_MISSING_MESSAGE)
    private int conflictScore;

    @NotNull(message = StoryReviewConstants.SCORE_MISSING_MESSAGE)
    private int plotScore;

    @NotNull(message = StoryReviewConstants.SCORE_MISSING_MESSAGE)
    private int settingScore;

    @NotNull(message = StoryReviewConstants.SCORE_MISSING_MESSAGE)
    private int themeScore;
}
