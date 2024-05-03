package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.StoryFavorite;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryFavoriteRepository extends JpaRepository<StoryFavorite, Integer> {
    List<StoryFavorite> findByUserProfileId(long userProfileId);
    @Transactional
    Long deleteByUserProfileIdAndStoryId(long userProfileId, long storyId);
    boolean existsByUserProfileIdAndStoryId(long userProfileId, long storyId);
}
