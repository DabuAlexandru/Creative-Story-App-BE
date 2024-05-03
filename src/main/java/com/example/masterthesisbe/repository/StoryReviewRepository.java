package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.StoryReview;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryReviewRepository extends JpaRepository<StoryReview, Integer> {
    Page<StoryReview> findAllByStoryId(long storyId, Pageable pageable);
    List<StoryReview> findByUserProfileId(long userProfileId);
    StoryReview findFirstByUserProfileIdAndStoryId(long userProfileId, long storyId);
    @Transactional
    Long deleteByUserProfileIdAndStoryId(long userProfileId, long storyId);
    boolean existsByUserProfileIdAndStoryId(long userProfileId, long storyId);
}
