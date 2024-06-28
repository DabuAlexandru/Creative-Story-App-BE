package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.DiscussionConstants;
import com.example.masterthesisbe.constants.DiscussionThreadConstants;
import com.example.masterthesisbe.dto.discussionThread.CreateDiscussionThreadRequestDto;
import com.example.masterthesisbe.dto.discussionThread.DiscussionThreadResponseDto;
import com.example.masterthesisbe.dto.discussionThread.DiscussionThreadWithCommCountResponseDto;
import com.example.masterthesisbe.dto.discussionThread.UpdateDiscussionThreadRequestDto;
import com.example.masterthesisbe.dto.general.CountPaginateResponseDto;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.helpers.mappers.DiscussionThreadMapper;
import com.example.masterthesisbe.model.*;
import com.example.masterthesisbe.repository.DiscussionRepository;
import com.example.masterthesisbe.repository.DiscussionThreadRepository;
import com.example.masterthesisbe.repository.DiscussionThreadVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class DiscussionThreadService {
    private final DiscussionThreadRepository discussionThreadRepository;
    private final DiscussionThreadVoteRepository discussionThreadVoteRepository;
    private final DiscussionRepository discussionRepository;
    private final DiscussionThreadMapper discussionThreadMapper;
    private final AuthenticationService authService;

    private DiscussionThreadWithCommCountResponseDto convertToCompleteResponseDto(DiscussionThread discussionThread) {
        UserProfile author = authService.getCurrentUserProfile();

        DiscussionThreadWithCommCountResponseDto responseDto = discussionThreadMapper.convertToResponseWithCountDto(discussionThread);

        int commentsCount = discussionThreadRepository.countAllByMainThreadId(discussionThread.getId());
        int voteValue = discussionThreadVoteRepository.getVoteSumByThreadId(discussionThread.getId());

        Optional<DiscussionThreadVote> foundVote =
                discussionThreadVoteRepository.findByUserProfileIdAndThreadId(author.getId(), discussionThread.getId());
        byte userVote = foundVote.isPresent() ? foundVote.get().getVoteValue() : 0;

        responseDto.setCommentsCount(commentsCount);
        responseDto.setVoteValue(voteValue);
        responseDto.setUserVote(userVote);

        return responseDto;
    }

    public Page<DiscussionThreadWithCommCountResponseDto> getDiscussionThreadsOfMainThreadPaginate(int mainThreadId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<DiscussionThread> discussionThreadsPagination = this.discussionThreadRepository.findAllPaginateByMainThreadIdOrderByCreatedOnDesc(mainThreadId, pageable);

        List<DiscussionThreadWithCommCountResponseDto> content = discussionThreadsPagination.getContent().stream()
                .map(this::convertToCompleteResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, discussionThreadsPagination.getPageable(), discussionThreadsPagination.getTotalElements());
    }

    public List<DiscussionThreadResponseDto> getAllDiscussionThreadsOfMainThread(int mainThreadId) {
        return discussionThreadRepository.findAllByMainThreadIdOrderByCreatedOnDesc(mainThreadId).stream()
                .map(discussionThreadMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public Page<DiscussionThreadResponseDto> getDiscussionThreadsOfAuthorPaginate(int authorId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<DiscussionThread> discussionThreadsPagination = this.discussionThreadRepository.findAllPaginateByAuthorId(authorId, pageable);

        List<DiscussionThreadResponseDto> content = discussionThreadsPagination.getContent().stream()
                .map(discussionThreadMapper::convertToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, discussionThreadsPagination.getPageable(), discussionThreadsPagination.getTotalElements());
    }

    public CountPaginateResponseDto getThreadsOfMainThreadPagesCount(int mainThreadId, int size) {
        int threadsCount = discussionThreadRepository.countAllByMainThreadId(mainThreadId);
        int pagesCount = (int)Math.ceil((double) threadsCount / size);

        return new CountPaginateResponseDto(threadsCount, pagesCount);
    }

    public CountPaginateResponseDto getThreadsOfDiscussionPagesCount(int discussionId, Integer mainThreadId, int size) {
        int threadsCount = discussionThreadRepository.countAllByDiscussionIdAndMainThreadId(discussionId, mainThreadId);
        int pagesCount = (int)Math.ceil((double) threadsCount / size);

        return new CountPaginateResponseDto(threadsCount, pagesCount);
    }

    public Page<DiscussionThreadWithCommCountResponseDto> getDiscussionThreadsOfDiscussionPaginate(int discussionId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<DiscussionThread> discussionThreadsPagination = this.discussionThreadRepository.findAllPaginateByDiscussionIdAndMainThreadIdOrderByCreatedOnDesc(discussionId, null, pageable);

        List<DiscussionThreadWithCommCountResponseDto> content = discussionThreadsPagination.getContent().stream()
                .map(this::convertToCompleteResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, discussionThreadsPagination.getPageable(), discussionThreadsPagination.getTotalElements());
    }

    public DiscussionThreadResponseDto getDiscussionThreadById(int discussionThreadId) {
        DiscussionThread foundThread = this.discussionThreadRepository.findById(discussionThreadId)
                .orElseThrow(() -> new ApiException(DiscussionThreadConstants.DISCUSSION_THREAD_NOT_FOUND_MESSAGE));
        return this.discussionThreadMapper.convertToResponseDto(foundThread);
    }
    
    public DiscussionThreadResponseDto createNewDiscussionThread(CreateDiscussionThreadRequestDto discussionThread) {
        DiscussionThread convertedDiscussionThread = discussionThreadMapper.convertFromCreateRequestDto(discussionThread);
        UserProfile author = authService.getCurrentUserProfile();
        convertedDiscussionThread.setAuthor(author);

        if (!isNull(discussionThread.getMainThreadId())) {
            DiscussionThread mainThread = discussionThreadRepository.findById(discussionThread.getMainThreadId())
                    .orElseThrow(() -> new ApiException(DiscussionThreadConstants.DISCUSSION_THREAD_NOT_FOUND_MESSAGE));
            convertedDiscussionThread.setMainThread(mainThread);
        }

        Discussion discussion = discussionRepository.findById(discussionThread.getDiscussionId())
                .orElseThrow(() -> new ApiException((DiscussionConstants.DISCUSSION_NOT_FOUND_MESSAGE)));

        convertedDiscussionThread.setDiscussion(discussion);

        DiscussionThread newDiscussionThread = discussionThreadRepository.save(convertedDiscussionThread);
        return discussionThreadMapper.convertToResponseDto(newDiscussionThread);
    }

    public DiscussionThreadResponseDto updateDiscussionThread(Integer discussionThreadId, UpdateDiscussionThreadRequestDto updatedDiscussionThread) {
        DiscussionThread discussionThread = this.discussionThreadRepository.findById(discussionThreadId)
                .orElseThrow(() -> new ApiException(DiscussionThreadConstants.DISCUSSION_THREAD_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if(loggedInUser.getId() != discussionThread.getAuthor().getUser().getId()) {
            throw new ApiException(DiscussionThreadConstants.NO_PERMISSION_TO_MODIFY);
        }

        discussionThreadMapper.updateDiscussionThreadWithDto(discussionThread, updatedDiscussionThread);
        return this.discussionThreadMapper.convertToResponseDto(discussionThreadRepository.save(discussionThread));
    }
}
