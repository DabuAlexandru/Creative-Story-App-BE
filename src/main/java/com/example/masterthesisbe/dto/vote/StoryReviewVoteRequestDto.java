package com.example.masterthesisbe.dto.vote;

import com.example.masterthesisbe.constants.StoryReviewConstants;
import com.example.masterthesisbe.constants.VoteConstants;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryReviewVoteRequestDto {
    @NotNull(message = StoryReviewConstants.STORY_REVIEW_NOT_NULL_CONSTRAINT_MESSAGE)
    private Integer storyReviewId;

    @NotNull(message = VoteConstants.VOTE_NOT_BLANK_CONSTRAINT_MESSAGE)
    private Integer voteValue;
}
