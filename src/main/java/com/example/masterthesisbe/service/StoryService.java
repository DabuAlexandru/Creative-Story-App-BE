package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.StoryConstants;
import com.example.masterthesisbe.dto.story.*;
import com.example.masterthesisbe.helpers.mappers.StoryMapper;
import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.model.User;
import com.example.masterthesisbe.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;
    private final AuthenticationService authService;

    public Page<StoryResponseDto> getStoriesPaginate(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Story> storiesPagination = this.storyRepository.findAll(pageable);

        List<StoryResponseDto> content = storiesPagination.getContent().stream()
                .map(storyMapper::convertToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, storiesPagination.getPageable(), storiesPagination.getTotalElements());
    }

    public List<StoryResponseDto> getAllStoriesOfAuthor(int authorId) {
        List<Story> foundStories = this.storyRepository.findByAuthorId(authorId);
        return foundStories
                .stream().map(storyMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public StoryResponseDto getStoryById(Integer storyId) {
        Story foundStory = this.storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException(StoryConstants.STORY_NOT_FOUND_MESSAGE));
        return storyMapper.convertToResponseDto(foundStory);
    }

    public StoryContentResponseDto getStoryContentById(Integer storyId) {
        Story foundStory = this.storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException(StoryConstants.STORY_NOT_FOUND_MESSAGE));
        return storyMapper.convertToContentResponseDto(foundStory);
    }

    public StoryResponseDto createNewStory(CreateStoryRequestDto story) {
        Story convertedStory = storyMapper.convertFromCreateRequestDto(story);
        User loggedInUser = authService.getLoggedInUser();
        convertedStory.setAuthor(loggedInUser);
        Story newStory = storyRepository.save(convertedStory);
        return storyMapper.convertToResponseDto(newStory);
    }

    public StoryResponseDto updateStory(Integer storyId, UpdateStoryRequestDto updatedStory) {
        Story story = this.storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException(StoryConstants.STORY_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if(loggedInUser.getId() != story.getAuthor().getId()) {
            throw new RuntimeException(StoryConstants.NO_PERMISSIONS_TO_MODIFY);
        }

        storyMapper.updateStoryWithDto(story, updatedStory);
        return this.storyMapper.convertToResponseDto(storyRepository.save(story));
    }
}
