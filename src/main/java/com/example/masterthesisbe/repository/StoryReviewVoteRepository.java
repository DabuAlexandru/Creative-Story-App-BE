package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.StoryReviewVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StoryReviewVoteRepository extends JpaRepository<StoryReviewVote, Integer> {

    Optional<StoryReviewVote> findByUserProfileIdAndReviewId(Integer userProfileId, Integer reviewId);

    @Query("SELECT COALESCE(SUM(srv.voteValue), 0) FROM StoryReviewVote srv WHERE srv.review.id = ?1")
    int getVoteSumByStoryReviewId(Integer storyReviewId);
}
