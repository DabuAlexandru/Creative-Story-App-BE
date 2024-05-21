package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.constants.StoryConstants;
import com.example.masterthesisbe.dto.story.CreateStoryRequestDto;
import com.example.masterthesisbe.dto.story.StoryContentResponseDto;
import com.example.masterthesisbe.dto.story.StoryResponseDto;
import com.example.masterthesisbe.dto.story.UpdateStoryRequestDto;
import com.example.masterthesisbe.helpers.handlers.ValidationHandler;
import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.service.StoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/of-author/{authorId}")
    public ResponseEntity<List<StoryResponseDto>> retrieveStoriesForAuthor(@PathVariable Integer authorId) {
        return ResponseEntity.ok().body(this.storyService.getAllStoriesOfAuthor(authorId));
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
