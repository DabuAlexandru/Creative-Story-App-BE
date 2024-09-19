package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.StoryConstants;
import com.example.masterthesisbe.dto.story.*;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.helpers.mappers.StoryMapper;
import com.example.masterthesisbe.model.*;
import com.example.masterthesisbe.repository.StoryRepository;
import com.example.masterthesisbe.specification.StorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;

    private final AuthenticationService authService;
    private final FileInstanceService fileInstanceService;

    private final StoryMapper storyMapper;

    private Story getPrivateStory(int storyId) {
        Story story = this.storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException(StoryConstants.STORY_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if(loggedInUser.getId() != story.getAuthor().getUser().getId()) {
            throw new ApiException(StoryConstants.NO_PERMISSIONS_TO_MODIFY);
        }

        return story;
    }

    private Story getStoryForReader(int storyId) {
        Story story = this.storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException(StoryConstants.STORY_NOT_FOUND_MESSAGE));

        if(!story.isPublished()) {
            User loggedInUser = authService.getLoggedInUser();
            if (loggedInUser.getId() != story.getAuthor().getUser().getId()) {
                throw new ApiException(StoryConstants.NO_PERMISSION_TO_VIEW);
            }
        }

        return story;
    }

    private Page<Story> getFilteredStories(StoryFilterRequestDto filtersPayload, Pageable pageable) {
        Specification<Story> spec = Specification.where(null);

        String title = filtersPayload.getTitle();
        if (title != null && !title.isEmpty()) {
            spec = spec.and(StorySpecification.titleContains(title));
        }

        List<Integer> authorIds = filtersPayload.getAuthorIds();
        if (authorIds != null && !authorIds.isEmpty()) {
            spec = spec.and(StorySpecification.authorIdIn(authorIds));
        }

        List<Integer> genreIds = filtersPayload.getGenreIds();
        if (genreIds != null && !genreIds.isEmpty()) {
            spec = spec.and(StorySpecification.genresIn(genreIds));
        }

        spec = spec.and(StorySpecification.isPublished(true));

        return storyRepository.findAll(spec, pageable);
    }

    public Page<StoryResponseDto> getFilteredStoriesPaginate(StoryFilterRequestDto filtersPayload, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Story> storiesPagination = getFilteredStories(filtersPayload, pageable);

        List<StoryResponseDto> content = storiesPagination.getContent().stream()
                .map(storyMapper::convertToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, storiesPagination.getPageable(), storiesPagination.getTotalElements());
    }

    public Page<StoryResponseDto> getStoriesPaginate(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Story> storiesPagination = this.storyRepository.findAll(pageable);

        List<StoryResponseDto> content = storiesPagination.getContent().stream()
                .map(storyMapper::convertToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, storiesPagination.getPageable(), storiesPagination.getTotalElements());
    }

    public List<StoryResponseDto> getAllStoriesOfAuthor(int authorId, boolean isPublished) {
        List<Story> foundStories = this.storyRepository.findByAuthorIdAndPublished(authorId, isPublished);
        return foundStories
                .stream().map(storyMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public void publishStory(Integer storyId) {
        Story foundStory = getPrivateStory(storyId);
        if (foundStory.isPublished()) {
            return;
        }
        foundStory.setPublished(true);
        this.storyRepository.save(foundStory);
    }

    public StoryResponseDto getStoryById(Integer storyId) {
        Story foundStory = getStoryForReader(storyId);
        return storyMapper.convertToResponseDto(foundStory);
    }

    public StoryContentResponseDto getStoryContentById(Integer storyId) {
        Story foundStory = this.storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException(StoryConstants.STORY_NOT_FOUND_MESSAGE));
        return storyMapper.convertToContentResponseDto(foundStory);
    }

    public StoryResponseDto createNewStory(CreateStoryRequestDto story) {
        Story convertedStory = storyMapper.convertFromCreateRequestDto(story);
        UserProfile author = authService.getCurrentUserProfile();
        convertedStory.setAuthor(author);

        Story newStory = storyRepository.save(convertedStory);

        return storyMapper.convertToResponseDto(newStory);
    }

    public StoryResponseDto updateStory(Integer storyId, UpdateStoryRequestDto updatedStory) {
        Story story = getPrivateStory(storyId);

        storyMapper.updateStoryWithDto(story, updatedStory);
        return this.storyMapper.convertToResponseDto(storyRepository.save(story));
    }

    public void uploadCoverPicture(int storyId, MultipartFile picture) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException("Story with id " + storyId + " couldn't be found!"));

        FileInstance newProfilePicture = fileInstanceService.userUploadFile(picture);
        story.setCoverPicture(newProfilePicture);
        storyRepository.save(story);
    }
}
