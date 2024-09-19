package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.dto.story.*;
import com.example.masterthesisbe.helpers.handlers.ValidationHandler;
import com.example.masterthesisbe.service.StoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/story")
@RequiredArgsConstructor
public class StoryController extends ValidationHandler {
    private final StoryService storyService;

    @GetMapping("/get-all")
    public ResponseEntity<Page<StoryResponseDto>> retrieveAllStories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok().body(this.storyService.getStoriesPaginate(page, size, sortBy));
    }

    @PostMapping("/get-all-filtered")
    public ResponseEntity<Page<StoryResponseDto>> retrieveAllStoriesFiltered(
            @RequestBody StoryFilterRequestDto filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok().body(this.storyService.getFilteredStoriesPaginate(filters, page, size, sortBy));
    }


    @GetMapping("/of-author/{authorId}/{isPublished}")
    public ResponseEntity<List<StoryResponseDto>> retrieveStoriesForAuthor(@PathVariable Integer authorId, @PathVariable boolean isPublished) {
        return ResponseEntity.ok().body(this.storyService.getAllStoriesOfAuthor(authorId, isPublished));
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<StoryResponseDto> retrieveStory(@PathVariable Integer storyId) {
        return ResponseEntity.ok().body(this.storyService.getStoryById(storyId));
    }

    @GetMapping("/content/{storyId}")
    public ResponseEntity<StoryContentResponseDto> retrieveStoryContent(@PathVariable Integer storyId) {
        return ResponseEntity.ok().body(this.storyService.getStoryContentById(storyId));
    }

    @PostMapping("/create")
    public ResponseEntity<StoryResponseDto> createNewStory(@Valid @RequestBody CreateStoryRequestDto newStory) {
        return ResponseEntity.ok().body(this.storyService.createNewStory(newStory));
    }

    @PostMapping("/publish/{storyId}")
    public ResponseEntity<Void> publishStory(@PathVariable Integer storyId) {
        this.storyService.publishStory(storyId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{storyId}")
    public ResponseEntity<StoryResponseDto> updateStory(@PathVariable Integer storyId, @Valid @RequestBody UpdateStoryRequestDto updatedStory) {
        return ResponseEntity.ok().body(this.storyService.updateStory(storyId, updatedStory));
    }

    @PostMapping("/update-picture/{storyId}")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable Integer storyId, @RequestParam("file") MultipartFile file) {
        this.storyService.uploadCoverPicture(storyId, file);
        return ResponseEntity.ok().body("You successfully uploaded the cover picture!");
    }
}
