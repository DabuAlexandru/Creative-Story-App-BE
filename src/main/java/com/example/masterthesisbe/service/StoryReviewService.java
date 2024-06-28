package com.example.masterthesisbe.service;

import com.example.masterthesisbe.dto.discussionThread.DiscussionThreadWithCommCountResponseDto;
import com.example.masterthesisbe.dto.storyReview.AddStoryReviewRequestDto;
import com.example.masterthesisbe.dto.storyReview.StoryReviewResponseDto;
import com.example.masterthesisbe.dto.storyReview.StoryReviewWithVotesResponseDto;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.helpers.mappers.StoryReviewMapper;
import com.example.masterthesisbe.model.*;
import com.example.masterthesisbe.repository.StoryRepository;
import com.example.masterthesisbe.repository.StoryReviewRepository;
import com.example.masterthesisbe.repository.StoryReviewVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class StoryReviewService {
    private final StoryReviewRepository storyReviewRepository;
    private final StoryReviewVoteRepository storyReviewVoteRepository;
    private final StoryRepository storyRepository;
    private final StoryOverallScoreService storyOverallScoreService;
    private final StoryReviewMapper storyReviewMapper;
    private final AuthenticationService authService;

    private StoryReviewWithVotesResponseDto convertToCompleteResponseDto(StoryReview storyReview) {
        UserProfile author = authService.getCurrentUserProfile();

        StoryReviewWithVotesResponseDto responseDto = storyReviewMapper.convertToVoteResponseDto(storyReview);

        int voteValue = storyReviewVoteRepository.getVoteSumByStoryReviewId(storyReview.getId());
        Optional<StoryReviewVote> foundVote =
                storyReviewVoteRepository.findByUserProfileIdAndReviewId(author.getId(), storyReview.getId());
        byte userVote = foundVote.isPresent() ? foundVote.get().getVoteValue() : 0;

        responseDto.setVoteValue(voteValue);
        responseDto.setUserVote(userVote);

        return responseDto;
    }

    public Page<StoryReviewWithVotesResponseDto> getStoryReviewsPaginate(int storyId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<StoryReview> storyReviewsPagination = this.storyReviewRepository.findAllByStoryId(storyId, pageable);

        List<StoryReviewWithVotesResponseDto> content = storyReviewsPagination.getContent().stream()
                .map(this::convertToCompleteResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, storyReviewsPagination.getPageable(), storyReviewsPagination.getTotalElements());
    }

    public StoryReviewResponseDto createReviewForStory(int storyId, AddStoryReviewRequestDto newReview)
    {
        UserProfile author = authService.getCurrentUserProfile();
        if (storyReviewRepository.existsByUserProfileIdAndStoryId(author.getId(), storyId)) {
            throw new ApiException("There is already a review for this story for the current user!");
        }

        StoryReview convertedReview = storyReviewMapper.convertFromCreateRequestDto(newReview);
        convertedReview.setUserProfile(author);

        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException("There is no story with id: " + storyId));
        if (story.getAuthor().getId() == author.getId()) {
            throw new ApiException("The author can't review it's own story");
        }

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

    public StoryReviewResponseDto getReviewByStoryAndProfile(int storyId, int userProfileId) {
        StoryReview foundReview = storyReviewRepository.findFirstByUserProfileIdAndStoryId(userProfileId, storyId);
        if (isNull(foundReview)) {
            return null;
        }
        return storyReviewMapper.convertToResponseDto(foundReview);
    }
}
