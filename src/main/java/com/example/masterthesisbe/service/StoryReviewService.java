package com.example.masterthesisbe.service;

import com.example.masterthesisbe.dto.storyReview.AddStoryReviewRequestDto;
import com.example.masterthesisbe.dto.storyReview.StoryReviewResponseDto;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.helpers.mappers.StoryReviewMapper;
import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.model.StoryReview;
import com.example.masterthesisbe.model.UserProfile;
import com.example.masterthesisbe.repository.StoryRepository;
import com.example.masterthesisbe.repository.StoryReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryReviewService {
    private final StoryReviewRepository storyReviewRepository;
    private final StoryRepository storyRepository;
    private final StoryOverallScoreService storyOverallScoreService;
    private final StoryReviewMapper storyReviewMapper;
    private final AuthenticationService authService;

    public Page<StoryReviewResponseDto> getStoryReviewsPaginate(int storyId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<StoryReview> storyReviewsPagination = this.storyReviewRepository.findAllByStoryId(storyId, pageable);

        List<StoryReviewResponseDto> content = storyReviewsPagination.getContent().stream()
                .map(storyReviewMapper::convertToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, storyReviewsPagination.getPageable(), storyReviewsPagination.getTotalElements());
    }

    public StoryReviewResponseDto createReviewForStory(int storyId, AddStoryReviewRequestDto newReview)
    {
        StoryReview convertedReview = storyReviewMapper.convertFromCreateRequestDto(newReview);
        UserProfile author = authService.getCurrentUserProfile();
        convertedReview.setUserProfile(author);

        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException("There is no story with id: " + storyId));
        convertedReview.setStory(story);

        storyOverallScoreService.addScoreToStory(storyId, convertedReview);

        StoryReview createdReview = storyReviewRepository.save(convertedReview);
        return storyReviewMapper.convertToResponseDto(createdReview);
    }

    public void deleteReviewOfStory(int storyId) {
        UserProfile userProfile = authService.getCurrentUserProfile();
        StoryReview reviewToBeDeleted = storyReviewRepository.findFirstByUserProfileIdAndStoryId(userProfile.getId(), storyId);

        Long deletedCount = storyReviewRepository.deleteByUserProfileIdAndStoryId(userProfile.getId(), storyId);
        if (deletedCount == 0) {
            throw new ApiException("There is no review for the story with id: " + storyId);
        }

        storyOverallScoreService.removeScoreFromStory(storyId, reviewToBeDeleted);
    }
}
