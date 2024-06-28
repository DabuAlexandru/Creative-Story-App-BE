package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.DiscussionThreadVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DiscussionThreadVoteRepository extends JpaRepository<DiscussionThreadVote, Integer> {

    Optional<DiscussionThreadVote> findByUserProfileIdAndThreadId(Integer userProfileId, Integer threadId);

    @Query("SELECT COALESCE(SUM(dtv.voteValue), 0) FROM DiscussionThreadVote dtv WHERE dtv.thread.id = ?1")
    int getVoteSumByThreadId(Integer threadId);
}
