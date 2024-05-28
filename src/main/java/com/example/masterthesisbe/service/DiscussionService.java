package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.DiscussionConstants;
import com.example.masterthesisbe.constants.StoryConstants;
import com.example.masterthesisbe.dto.discussion.CreateDiscussionRequestDto;
import com.example.masterthesisbe.dto.discussion.DiscussionResponseDto;
import com.example.masterthesisbe.dto.discussion.UpdateDiscussionRequestDto;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.helpers.mappers.DiscussionMapper;
import com.example.masterthesisbe.model.Discussion;
import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.model.User;
import com.example.masterthesisbe.model.UserProfile;
import com.example.masterthesisbe.repository.DiscussionRepository;
import com.example.masterthesisbe.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final StoryRepository storyRepository;
    private final DiscussionMapper discussionMapper;
    private final AuthenticationService authService;

    public Page<DiscussionResponseDto> getDiscussionsPaginate(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Discussion> discussionsPagination = this.discussionRepository.findAll(pageable);

        List<DiscussionResponseDto> content = discussionsPagination.getContent().stream()
                .map(discussionMapper::convertToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, discussionsPagination.getPageable(), discussionsPagination.getTotalElements());
    }

    public Page<DiscussionResponseDto> getDiscussionsOfAuthorPaginate(int authorId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Discussion> discussionsPagination = this.discussionRepository.findAllPaginateByAuthorId(authorId, pageable);

        List<DiscussionResponseDto> content = discussionsPagination.getContent().stream()
                .map(discussionMapper::convertToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, discussionsPagination.getPageable(), discussionsPagination.getTotalElements());
    }

    public Page<DiscussionResponseDto> getDiscussionsOfStoryPaginate(int storyId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Discussion> discussionsPagination = this.discussionRepository.findAllPaginateByStoryId(storyId, pageable);

        List<DiscussionResponseDto> content = discussionsPagination.getContent().stream()
                .map(discussionMapper::convertToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, discussionsPagination.getPageable(), discussionsPagination.getTotalElements());
    }

    public DiscussionResponseDto getDiscussionById(Integer discussionId) {
        Discussion foundDiscussion = this.discussionRepository.findById(discussionId)
                .orElseThrow(() -> new ApiException(DiscussionConstants.DISCUSSION_NOT_FOUND_MESSAGE));
        return discussionMapper.convertToResponseDto(foundDiscussion);
    }

    public DiscussionResponseDto createNewDiscussion(CreateDiscussionRequestDto discussion) {
        Discussion convertedDiscussion = discussionMapper.convertFromCreateRequestDto(discussion);
        UserProfile author = authService.getCurrentUserProfile();
        convertedDiscussion.setAuthor(author);

        if (!isNull(discussion.getStoryId())) {
            Story story = storyRepository.findById(discussion.getStoryId())
                    .orElseThrow(() -> new ApiException(StoryConstants.STORY_NOT_FOUND_MESSAGE));
            convertedDiscussion.setStory(story);
        }

        Discussion newDiscussion = discussionRepository.save(convertedDiscussion);
        return discussionMapper.convertToResponseDto(newDiscussion);
    }

    public DiscussionResponseDto updateDiscussion(Integer discussionId, UpdateDiscussionRequestDto updatedDiscussion) {
        Discussion discussion = this.discussionRepository.findById(discussionId)
                .orElseThrow(() -> new ApiException(DiscussionConstants.DISCUSSION_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if(loggedInUser.getId() != discussion.getAuthor().getUser().getId()) {
            throw new ApiException(DiscussionConstants.NO_PERMISSION_TO_MODIFY);
        }

        discussionMapper.updateDiscussionWithDto(discussion, updatedDiscussion);
        return this.discussionMapper.convertToResponseDto(discussionRepository.save(discussion));
    }
}
