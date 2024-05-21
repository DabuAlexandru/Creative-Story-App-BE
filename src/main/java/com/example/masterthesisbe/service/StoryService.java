package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.StoryConstants;
import com.example.masterthesisbe.dto.story.*;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.helpers.mappers.StoryMapper;
import com.example.masterthesisbe.model.*;
import com.example.masterthesisbe.repository.FileInstanceRepository;
import com.example.masterthesisbe.repository.GenreRepository;
import com.example.masterthesisbe.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;
    private final GenreRepository genreRepository;
    private final FileInstanceRepository fileInstanceRepository;

    private final AuthenticationService authService;
    private final FileInstanceService fileInstanceService;

    private final StoryMapper storyMapper;

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
                .orElseThrow(() -> new ApiException(StoryConstants.STORY_NOT_FOUND_MESSAGE));
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
        UpdateGenresForStory(newStory, story.getGenreIds());

        return storyMapper.convertToResponseDto(newStory);
    }

    public StoryResponseDto updateStory(Integer storyId, UpdateStoryRequestDto updatedStory) {
        Story story = this.storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException(StoryConstants.STORY_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if(loggedInUser.getId() != story.getAuthor().getUser().getId()) {
            throw new ApiException(StoryConstants.NO_PERMISSIONS_TO_MODIFY);
        }

//        UpdateGenresForStory(story, updatedStory.getGenreIds());

        storyMapper.updateStoryWithDto(story, updatedStory);
        return this.storyMapper.convertToResponseDto(storyRepository.save(story));
    }

    private void UpdateGenresForStory(Story story, Set<Integer> genreIds) {
        Set<Genre> genres = new HashSet<>();
        for (Integer genreId : genreIds) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new ApiException("Genre not found with id: " + genreId));
            genres.add(genre);
        }
        story.setGenres(genres);
    }

    public void uploadCoverPicture(int storyId, MultipartFile picture) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException("Story with id " + storyId + " couldn't be found!"));

        FileInstance newProfilePicture = fileInstanceService.userUploadFile(picture);
        story.setCoverPicture(newProfilePicture);
        storyRepository.save(story);
    }
}
