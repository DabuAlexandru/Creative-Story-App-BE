package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.discussionThread.CreateDiscussionThreadRequestDto;
import com.example.masterthesisbe.dto.discussionThread.DiscussionThreadResponseDto;
import com.example.masterthesisbe.dto.discussionThread.DiscussionThreadWithCommCountResponseDto;
import com.example.masterthesisbe.dto.discussionThread.UpdateDiscussionThreadRequestDto;
import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import com.example.masterthesisbe.model.DiscussionThread;
import com.example.masterthesisbe.model.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class DiscussionThreadMapper {
    private final UserProfileMapper userProfileMapper;

    public DiscussionThreadWithCommCountResponseDto convertToResponseWithCountDto(DiscussionThread discussionThread) {
        if (isNull(discussionThread)) {
            return null;
        }

        UserProfile author = discussionThread.getAuthor();
        UserProfileReferenceResponseDto convertedAuthor = userProfileMapper.convertToReferenceResponseDto(author);

        return new DiscussionThreadWithCommCountResponseDto(
                discussionThread.getId(),
                discussionThread.getContent(),
                convertedAuthor,
                0,
                0,
                (byte) 0,
                discussionThread.getCreatedOn(),
                discussionThread.getLastUpdatedOn()
        );
    }

    public DiscussionThreadResponseDto convertToResponseDto(DiscussionThread discussionThread) {
        if (isNull(discussionThread)) {
            return null;
        }

        UserProfile author = discussionThread.getAuthor();
        UserProfileReferenceResponseDto convertedAuthor = userProfileMapper.convertToReferenceResponseDto(author);

        return new DiscussionThreadResponseDto(
                discussionThread.getId(),
                discussionThread.getContent(),
                convertedAuthor,
                discussionThread.getCreatedOn(),
                discussionThread.getLastUpdatedOn()
        );
    }

    public DiscussionThread convertFromCreateRequestDto(CreateDiscussionThreadRequestDto newDiscussionThread) {
        if(isNull(newDiscussionThread)) {
            return null;
        }

        return new DiscussionThread(
                newDiscussionThread.getContent()
        );
    }

    public void updateDiscussionThreadWithDto(DiscussionThread discussionThread, UpdateDiscussionThreadRequestDto discussionThreadRequestDto) {
        discussionThread.setContent(discussionThreadRequestDto.getContent());
    }
}
