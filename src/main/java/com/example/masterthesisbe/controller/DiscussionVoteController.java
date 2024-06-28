package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.dto.vote.DiscussionVoteRequestDto;
import com.example.masterthesisbe.helpers.handlers.ValidationHandler;
import com.example.masterthesisbe.service.DiscussionVoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/discussion-vote")
@RequiredArgsConstructor
public class DiscussionVoteController extends ValidationHandler {
    private final DiscussionVoteService discussionVoteService;

    @PostMapping("/vote")
    public ResponseEntity<Void> vote(@Valid @RequestBody DiscussionVoteRequestDto voteRequestDto) {
        discussionVoteService.vote(voteRequestDto.getDiscussionId(), voteRequestDto.getVoteValue().byteValue());
        return ResponseEntity.ok().build();
    }
}
