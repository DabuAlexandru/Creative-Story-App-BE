package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.StoryOverallScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryOverallScoreRepository extends JpaRepository<StoryOverallScore, Integer> {
    StoryOverallScore findFirstByStoryId(int storyId);
}
