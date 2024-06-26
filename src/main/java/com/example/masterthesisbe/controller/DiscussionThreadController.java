package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.dto.discussionThread.CreateDiscussionThreadRequestDto;
import com.example.masterthesisbe.dto.discussionThread.DiscussionThreadResponseDto;
import com.example.masterthesisbe.dto.discussionThread.DiscussionThreadWithCommCountResponseDto;
import com.example.masterthesisbe.dto.discussionThread.UpdateDiscussionThreadRequestDto;
import com.example.masterthesisbe.dto.general.CountPaginateResponseDto;
import com.example.masterthesisbe.helpers.handlers.ValidationHandler;
import com.example.masterthesisbe.service.DiscussionThreadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/discussion-thread")
@RequiredArgsConstructor
public class DiscussionThreadController extends ValidationHandler {
    private final DiscussionThreadService discussionThreadService;

    @GetMapping("/get-all/of-thread/paginate/{mainThreadId}")
    public ResponseEntity<Page<DiscussionThreadWithCommCountResponseDto>> retrieveAllDiscussionThreadOfMainThreadPaginate(
            @PathVariable Integer mainThreadId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok().body(this.discussionThreadService.getDiscussionThreadsOfMainThreadPaginate(mainThreadId, page, size, sortBy));
    }

    @GetMapping("/get-pages-count/of-thread/{mainThreadId}")
    public ResponseEntity<CountPaginateResponseDto> retrieveThreadsOfMainThreadPagesCount(
            @PathVariable Integer mainThreadId,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok().body(
                this.discussionThreadService.getThreadsOfMainThreadPagesCount(mainThreadId, size)
        );
    }

    @GetMapping("/get-all/of-thread/{mainThreadId}")
    public ResponseEntity<List<DiscussionThreadResponseDto>> retrieveAllDiscussionThreadOfMainThread(
            @PathVariable Integer mainThreadId) {
        return ResponseEntity.ok().body(this.discussionThreadService.getAllDiscussionThreadsOfMainThread(mainThreadId));
    }

    @GetMapping("/get-all/of-author/paginate/{authorId}")
    public ResponseEntity<Page<DiscussionThreadResponseDto>> retrieveAllDiscussionThreadsOfAuthor(
            @PathVariable Integer authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok().body(
                this.discussionThreadService.getDiscussionThreadsOfAuthorPaginate(authorId, page, size, sortBy)
        );
    }

    @GetMapping("/get-pages-count/of-discussion/{discussionId}")
    public ResponseEntity<CountPaginateResponseDto> retrieveThreadsOfDiscussionPagesCount(
            @PathVariable Integer discussionId,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok().body(
                this.discussionThreadService.getThreadsOfDiscussionPagesCount(discussionId, null, size)
        );
    }

    @GetMapping("/get-all/of-discussion/paginate/{discussionId}")
    public ResponseEntity<Page<DiscussionThreadWithCommCountResponseDto>> retrieveAllDiscussionThreadsOfDiscussion(
            @PathVariable Integer discussionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok().body(
                this.discussionThreadService.getDiscussionThreadsOfDiscussionPaginate(discussionId, page, size, sortBy)
        );
    }

    @GetMapping("/{discussionThreadId}")
    public ResponseEntity<DiscussionThreadResponseDto> retrieveDiscussionThread(
            @PathVariable Integer discussionThreadId) {
        return ResponseEntity.ok().body(
                this.discussionThreadService.getDiscussionThreadById(discussionThreadId)
        );
    }
    
    @PostMapping("/create")
    public ResponseEntity<DiscussionThreadResponseDto> createDiscussionThread(
            @Valid @RequestBody CreateDiscussionThreadRequestDto newDiscussionThread) {
        return ResponseEntity.ok().body(
                this.discussionThreadService.createNewDiscussionThread(newDiscussionThread)
        );
    }

    @PutMapping("/update/{discussionThreadId}")
    public ResponseEntity<DiscussionThreadResponseDto> updateDiscussionThread(
            @PathVariable Integer discussionThreadId,
            @Valid @RequestBody UpdateDiscussionThreadRequestDto updatedDiscussionThread) {
        return ResponseEntity.ok().body(
                this.discussionThreadService.updateDiscussionThread(discussionThreadId, updatedDiscussionThread)
        );
    }
}
