package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.dto.userProfile.StoryFavoriteResponseDto;
import com.example.masterthesisbe.model.StoryFavorite;
import com.example.masterthesisbe.model.StoryReadLater;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface StoryFavoriteRepository extends JpaRepository<StoryFavorite, Integer> {
    Set<StoryFavorite> findByUserProfileId(long userProfileId);
    @Transactional
    Long deleteByUserProfileIdAndStoryId(long userProfileId, long storyId);
    boolean existsByUserProfileIdAndStoryId(long userProfileId, long storyId);
}
