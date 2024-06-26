package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.StoryConstants;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.model.StoryOverallScore;
import com.example.masterthesisbe.model.StoryReview;
import com.example.masterthesisbe.repository.StoryOverallScoreRepository;
import com.example.masterthesisbe.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class StoryOverallScoreService {
    private final StoryOverallScoreRepository storyOverallScoreRepository;
    private final StoryRepository storyRepository;

    private double getScoreWithNew(double overallScore, int numOfReviews, int newRating) {
        return ((overallScore * numOfReviews) + newRating) / (numOfReviews + 1.0);
    }

    private double getScoreWithRemoved(double overallScore, int numOfReviews, int oldRating) {
        return ((overallScore * numOfReviews) - oldRating) / (numOfReviews - 1.0);
    }

    private StoryOverallScore getOrCreateOverallScoreObjectForStory(int storyId) {
        Story foundStory = this.storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException(StoryConstants.STORY_NOT_FOUND_MESSAGE));

        if (storyOverallScoreRepository.existsByStoryId(storyId)) {
            StoryOverallScore foundOverallScore = storyOverallScoreRepository.findFirstByStoryId(storyId);
            if (isNull(foundStory.getStoryOverallScore())) {
                foundStory.setStoryOverallScore(foundOverallScore);
                storyRepository.save(foundStory);
            }
            return foundOverallScore;
        }

        StoryOverallScore newScoreSystem = new StoryOverallScore();

        newScoreSystem.setStory(foundStory);
        StoryOverallScore newOverallScore = storyOverallScoreRepository.save(newScoreSystem);

        foundStory.setStoryOverallScore(newOverallScore);
        storyRepository.save(foundStory);

        return newOverallScore;
    }

    public void addScoreToStory(int storyId, StoryReview newReview) {
        StoryOverallScore storyScore = this.getOrCreateOverallScoreObjectForStory(storyId);

        int numOfReviews = storyScore.getNumOfReviews();

        double oldCharacterScore = storyScore.getCharacterScore();
        double newCharacterScore = getScoreWithNew(oldCharacterScore, numOfReviews, newReview.getCharacterScore());
        storyScore.setCharacterScore(newCharacterScore);

        double oldConflictScore = storyScore.getConflictScore();
        double newConflictScore = getScoreWithNew(oldConflictScore, numOfReviews, newReview.getConflictScore());
        storyScore.setConflictScore(newConflictScore);

        double oldPlotScore = storyScore.getPlotScore();
        double newPlotScore = getScoreWithNew(oldPlotScore, numOfReviews, newReview.getPlotScore());
        storyScore.setPlotScore(newPlotScore);

        double oldSettingScore = storyScore.getSettingScore();
        double newSettingScore = getScoreWithNew(oldSettingScore, numOfReviews, newReview.getSettingScore());
        storyScore.setSettingScore(newSettingScore);

        double oldThemeScore = storyScore.getThemeScore();
        double newThemeScore = getScoreWithNew(oldThemeScore, numOfReviews, newReview.getThemeScore());
        storyScore.setThemeScore(newThemeScore);

        storyScore.setNumOfReviews(numOfReviews + 1);

        storyOverallScoreRepository.save(storyScore);
    }

    public void removeScoreFromStory(int storyId, StoryReview oldReview) {
        StoryOverallScore storyScore = storyOverallScoreRepository.findFirstByStoryId(storyId);
        if (storyScore == null || storyScore.getNumOfReviews() == 0) {
            return;
        }

        int numOfReviews = storyScore.getNumOfReviews();

        double oldCharacterScore = storyScore.getCharacterScore();
        double newCharacterScore = getScoreWithRemoved(oldCharacterScore, numOfReviews, oldReview.getCharacterScore());
        storyScore.setCharacterScore(newCharacterScore);

        double oldConflictScore = storyScore.getConflictScore();
        double newConflictScore = getScoreWithRemoved(oldConflictScore, numOfReviews, oldReview.getConflictScore());
        storyScore.setConflictScore(newConflictScore);

        double oldPlotScore = storyScore.getPlotScore();
        double newPlotScore = getScoreWithRemoved(oldPlotScore, numOfReviews, oldReview.getPlotScore());
        storyScore.setPlotScore(newPlotScore);

        double oldSettingScore = storyScore.getSettingScore();
        double newSettingScore = getScoreWithRemoved(oldSettingScore, numOfReviews, oldReview.getSettingScore());
        storyScore.setSettingScore(newSettingScore);

        double oldThemeScore = storyScore.getThemeScore();
        double newThemeScore = getScoreWithRemoved(oldThemeScore, numOfReviews, oldReview.getThemeScore());
        storyScore.setThemeScore(newThemeScore);

        storyScore.setNumOfReviews(numOfReviews - 1);

        storyOverallScoreRepository.save(storyScore);
    }
}
