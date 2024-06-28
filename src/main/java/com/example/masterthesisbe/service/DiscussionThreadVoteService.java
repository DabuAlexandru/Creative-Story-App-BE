package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.DiscussionThreadConstants;
import com.example.masterthesisbe.constants.VoteConstants;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.model.DiscussionThread;
import com.example.masterthesisbe.model.DiscussionThreadVote;
import com.example.masterthesisbe.model.UserProfile;
import com.example.masterthesisbe.repository.DiscussionThreadRepository;
import com.example.masterthesisbe.repository.DiscussionThreadVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscussionThreadVoteService {
    private final DiscussionThreadVoteRepository discussionThreadVoteRepository;
    private final DiscussionThreadRepository discussionThreadRepository;
    private final AuthenticationService authService;

    public void vote(Integer discussionThreadId, Byte voteValue) {
        if(voteValue != 1 && voteValue != -1) {
            throw new ApiException(VoteConstants.VOTE_NOT_VALID_CONSTRAINT_MESSAGE);
        }
        UserProfile author = authService.getCurrentUserProfile();
        Optional<DiscussionThreadVote> existingVote = discussionThreadVoteRepository.findByUserProfileIdAndThreadId(author.getId(), discussionThreadId);

        if (existingVote.isPresent()) {
            DiscussionThreadVote vote = existingVote.get();
            if (vote.getVoteValue().equals(voteValue)) {
                discussionThreadVoteRepository.delete(vote);
            } else {
                vote.setVoteValue(voteValue);
                discussionThreadVoteRepository.save(vote);
            }
        } else {
            DiscussionThread foundDiscussionThread = this.discussionThreadRepository.findById(discussionThreadId)
                    .orElseThrow(() -> new ApiException(DiscussionThreadConstants.DISCUSSION_THREAD_NOT_FOUND_MESSAGE));
            DiscussionThreadVote newVote = new DiscussionThreadVote(author, foundDiscussionThread, voteValue);
            discussionThreadVoteRepository.save(newVote);
        }
    }

    public long getVoteCount(Integer discussionThreadId) {
        return discussionThreadVoteRepository.getVoteSumByThreadId(discussionThreadId);
    }
}
