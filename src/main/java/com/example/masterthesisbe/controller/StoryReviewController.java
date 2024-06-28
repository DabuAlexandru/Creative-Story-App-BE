package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.constants.StoryReviewConstants;
import com.example.masterthesisbe.dto.storyReview.AddStoryReviewRequestDto;
import com.example.masterthesisbe.dto.storyReview.StoryReviewResponseDto;
import com.example.masterthesisbe.dto.storyReview.StoryReviewWithVotesResponseDto;
import com.example.masterthesisbe.service.StoryReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/review")
@RequiredArgsConstructor
public class StoryReviewController {
    private final StoryReviewService storyReviewService;

    @GetMapping("/get-all/of-story/{storyId}")
    public ResponseEntity<Page<StoryReviewWithVotesResponseDto>> retrieveAllReviewsOfStory(
            @PathVariable Integer storyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok().body(this.storyReviewService.getStoryReviewsPaginate(storyId, page, size, sortBy));
    }

    @PostMapping("/create/for-story/{storyId}")
    public ResponseEntity<StoryReviewResponseDto> createReviewForStory(
            @PathVariable Integer storyId,
            @RequestBody AddStoryReviewRequestDto newReview
    ) {
        return ResponseEntity.ok().body(this.storyReviewService.createReviewForStory(storyId, newReview));
    }

    @DeleteMapping("/delete/from-story/{storyId}")
    public ResponseEntity<String> deleteReviewFromStory(@PathVariable Integer storyId) {
        this.storyReviewService.deleteReviewOfStory(storyId);
        return ResponseEntity.ok().body(StoryReviewConstants.DELETE_OK_MESSAGE);
    }

    @GetMapping("/get/for-story/{storyId}/of-profile/{userProfileId}")
    public ResponseEntity<StoryReviewResponseDto> getReviewByStoryAndProfile(@PathVariable Integer storyId, @PathVariable Integer userProfileId) {
        return ResponseEntity.ok().body(this.storyReviewService.getReviewByStoryAndProfile(storyId, userProfileId));
    }
}
