package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.StoryReviewConstants;
import com.example.masterthesisbe.constants.VoteConstants;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.model.StoryReview;
import com.example.masterthesisbe.model.StoryReviewVote;
import com.example.masterthesisbe.model.UserProfile;
import com.example.masterthesisbe.repository.StoryReviewRepository;
import com.example.masterthesisbe.repository.StoryReviewVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoryReviewVoteService {
    private final StoryReviewVoteRepository storyReviewVoteRepository;
    private final StoryReviewRepository storyReviewRepository;
    private final AuthenticationService authService;

    public void vote(Integer storyReviewId, Byte voteValue) {
        if(voteValue != 1 && voteValue != -1) {
            throw new ApiException(VoteConstants.VOTE_NOT_VALID_CONSTRAINT_MESSAGE);
        }
        UserProfile author = authService.getCurrentUserProfile();
        Optional<StoryReviewVote> existingVote = storyReviewVoteRepository.findByUserProfileIdAndReviewId(author.getId(), storyReviewId);

        if (existingVote.isPresent()) {
            StoryReviewVote vote = existingVote.get();
            if (vote.getVoteValue().equals(voteValue)) {
                storyReviewVoteRepository.delete(vote);
            } else {
                vote.setVoteValue(voteValue);
                storyReviewVoteRepository.save(vote);
            }
        } else {
            StoryReview foundStoryReview = this.storyReviewRepository.findById(storyReviewId)
                    .orElseThrow(() -> new ApiException(StoryReviewConstants.STORY_REVIEW_NOT_FOUND_MESSAGE));
            StoryReviewVote newVote = new StoryReviewVote(author, foundStoryReview, voteValue);
            storyReviewVoteRepository.save(newVote);
        }
    }

    public long getVoteCount(Integer storyReviewId) {
        return storyReviewVoteRepository.getVoteSumByStoryReviewId(storyReviewId);
    }
}
