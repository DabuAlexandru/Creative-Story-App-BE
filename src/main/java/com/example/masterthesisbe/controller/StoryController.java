package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.constants.StoryConstants;
import com.example.masterthesisbe.dto.story.CreateStoryRequestDto;
import com.example.masterthesisbe.dto.story.StoryContentResponseDto;
import com.example.masterthesisbe.dto.story.StoryResponseDto;
import com.example.masterthesisbe.dto.story.UpdateStoryRequestDto;
import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.service.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/story")
public class StoryController {
    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/ofUser/{userId}")
    public ResponseEntity<List<StoryResponseDto>> retrieveStoriesForAuthor(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(this.storyService.getAllStoriesOfUser(userId));
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
    public ResponseEntity<StoryResponseDto> createNewStory(@RequestBody CreateStoryRequestDto newStory) {
        return ResponseEntity.ok().body(this.storyService.createNewStory(newStory));
    }

    @PutMapping("/update/{storyId}")
    public ResponseEntity<StoryResponseDto> updateStory(@PathVariable Integer storyId, @RequestBody UpdateStoryRequestDto updatedStory) {
        return ResponseEntity.ok().body(this.storyService.updateStory(storyId, updatedStory));
    }
}
