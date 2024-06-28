package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.dto.vote.StoryReviewVoteRequestDto;
import com.example.masterthesisbe.helpers.handlers.ValidationHandler;
import com.example.masterthesisbe.service.StoryReviewVoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/story-review-vote")
@RequiredArgsConstructor
public class StoryReviewVoteController extends ValidationHandler {
    private final StoryReviewVoteService storyReviewVoteService;

    @PostMapping("/vote")
    public ResponseEntity<Void> vote(@Valid @RequestBody StoryReviewVoteRequestDto voteRequestDto) {
        storyReviewVoteService.vote(voteRequestDto.getStoryReviewId(), voteRequestDto.getVoteValue().byteValue());
        return ResponseEntity.ok().build();
    }
}
