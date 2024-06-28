package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.DiscussionConstants;
import com.example.masterthesisbe.constants.VoteConstants;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.model.Discussion;
import com.example.masterthesisbe.model.DiscussionVote;
import com.example.masterthesisbe.model.UserProfile;
import com.example.masterthesisbe.repository.DiscussionRepository;
import com.example.masterthesisbe.repository.DiscussionVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscussionVoteService {
    private final DiscussionVoteRepository discussionVoteRepository;
    private final DiscussionRepository discussionRepository;
    private final AuthenticationService authService;

    public void vote(Integer discussionId, Byte voteValue) {
        if(voteValue != 1 && voteValue != -1) {
            throw new ApiException(VoteConstants.VOTE_NOT_VALID_CONSTRAINT_MESSAGE);
        }
        UserProfile author = authService.getCurrentUserProfile();
        Optional<DiscussionVote> existingVote = discussionVoteRepository.findByUserProfileIdAndDiscussionId(author.getId(), discussionId);

        if (existingVote.isPresent()) {
            DiscussionVote vote = existingVote.get();
            if (vote.getVoteValue().equals(voteValue)) {
                discussionVoteRepository.delete(vote);
            } else {
                vote.setVoteValue(voteValue);
                discussionVoteRepository.save(vote);
            }
        } else {
            Discussion foundDiscussion = this.discussionRepository.findById(discussionId)
                    .orElseThrow(() -> new ApiException(DiscussionConstants.DISCUSSION_NOT_FOUND_MESSAGE));
            DiscussionVote newVote = new DiscussionVote(author, foundDiscussion, voteValue);
            discussionVoteRepository.save(newVote);
        }
    }

    public long getVoteCount(Integer discussionId) {
        return discussionVoteRepository.getVoteSumByDiscussionId(discussionId);
    }
}
