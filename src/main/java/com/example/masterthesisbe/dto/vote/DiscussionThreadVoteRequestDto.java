package com.example.masterthesisbe.dto.vote;

import com.example.masterthesisbe.constants.DiscussionThreadConstants;
import com.example.masterthesisbe.constants.VoteConstants;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionThreadVoteRequestDto {
    @NotNull(message = DiscussionThreadConstants.DISCUSSION_THREAD_NOT_NULL_CONSTRAINT_MESSAGE)
    private Integer threadId;

    @NotNull(message = VoteConstants.VOTE_NOT_BLANK_CONSTRAINT_MESSAGE)
    private Integer voteValue;
}
