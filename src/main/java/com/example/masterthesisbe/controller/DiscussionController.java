package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.dto.discussion.CreateDiscussionRequestDto;
import com.example.masterthesisbe.dto.discussion.DiscussionResponseDto;
import com.example.masterthesisbe.dto.discussion.UpdateDiscussionRequestDto;
import com.example.masterthesisbe.helpers.handlers.ValidationHandler;
import com.example.masterthesisbe.service.DiscussionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/discussion")
@RequiredArgsConstructor
public class DiscussionController extends ValidationHandler {
    private final DiscussionService discussionService;

    @GetMapping("/get-all")
    public ResponseEntity<Page<DiscussionResponseDto>> retrieveAllDiscussions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok().body(this.discussionService.getDiscussionsPaginate(page, size, sortBy));
    }

    @GetMapping("/get-all/of-author/{authorId}")
    public ResponseEntity<Page<DiscussionResponseDto>> retrieveAllDiscussionsOfAuthor(
            @PathVariable Integer authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok().body(this.discussionService.getDiscussionsOfAuthorPaginate(authorId, page, size, sortBy));
    }

    @GetMapping("/get-all/of-story/{storyId}")
    public ResponseEntity<Page<DiscussionResponseDto>> retrieveAllDiscussionsOfStory(
            @PathVariable Integer storyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok().body(this.discussionService.getDiscussionsOfStoryPaginate(storyId, page, size, sortBy));
    }

    @GetMapping("/{discussionId}")
    public ResponseEntity<DiscussionResponseDto> retrieveDiscussion(@PathVariable Integer discussionId) {
        return ResponseEntity.ok().body(this.discussionService.getDiscussionById(discussionId));
    }

    @PostMapping("/create")
    public ResponseEntity<DiscussionResponseDto> createDiscussion(@Valid @RequestBody CreateDiscussionRequestDto newDiscussion) {
        return ResponseEntity.ok().body(this.discussionService.createNewDiscussion(newDiscussion));
    }

    @PutMapping("/update/{discussionId}")
    public ResponseEntity<DiscussionResponseDto> updateDiscussion(@PathVariable Integer discussionId, @Valid @RequestBody UpdateDiscussionRequestDto updatedDiscussion) {
        return ResponseEntity.ok().body(this.discussionService.updateDiscussion(discussionId, updatedDiscussion));
    }
}
