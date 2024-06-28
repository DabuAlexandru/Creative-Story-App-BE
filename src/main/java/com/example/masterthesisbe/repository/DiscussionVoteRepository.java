package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.DiscussionVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DiscussionVoteRepository extends JpaRepository<DiscussionVote, Integer> {

    Optional<DiscussionVote> findByUserProfileIdAndDiscussionId(Integer userProfileId, Integer discussionId);

    @Query("SELECT COALESCE(SUM(dv.voteValue), 0) FROM DiscussionVote dv WHERE dv.discussion.id = ?1")
    int getVoteSumByDiscussionId(Integer discussionId);
}
