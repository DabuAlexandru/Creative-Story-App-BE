package com.example.masterthesisbe.dto.vote;

import com.example.masterthesisbe.constants.DiscussionConstants;
import com.example.masterthesisbe.constants.VoteConstants;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionVoteRequestDto {
    @NotNull(message = DiscussionConstants.DISCUSSION_NOT_NULL_CONSTRAINT_MESSAGE)
    private Integer discussionId;

    @NotNull(message = VoteConstants.VOTE_NOT_BLANK_CONSTRAINT_MESSAGE)
    private Integer voteValue;
}
