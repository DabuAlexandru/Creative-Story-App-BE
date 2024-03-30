package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.StoryReadLater;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface StoryReadLaterRepository extends JpaRepository<StoryReadLater, Integer> {
    Set<StoryReadLater> findByUserProfileId(long userProfileId);
    @Transactional
    Long deleteByUserProfileIdAndStoryId(long userProfileId, long storyId);
    boolean existsByUserProfileIdAndStoryId(long userProfileId, long storyId);
}
