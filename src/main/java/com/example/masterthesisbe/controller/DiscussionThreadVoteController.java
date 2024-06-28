package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.dto.vote.DiscussionThreadVoteRequestDto;
import com.example.masterthesisbe.helpers.handlers.ValidationHandler;
import com.example.masterthesisbe.service.DiscussionThreadVoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/discussion-thread-vote")
@RequiredArgsConstructor
public class DiscussionThreadVoteController extends ValidationHandler {
    private final DiscussionThreadVoteService discussionThreadVoteService;

    @PostMapping("/vote")
    public ResponseEntity<Void> vote(@Valid @RequestBody DiscussionThreadVoteRequestDto voteRequestDto) {
        discussionThreadVoteService.vote(voteRequestDto.getThreadId(), voteRequestDto.getVoteValue().byteValue());
        return ResponseEntity.ok().build();
    }
}
