package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.discussion.CreateDiscussionRequestDto;
import com.example.masterthesisbe.dto.discussion.DiscussionResponseDto;
import com.example.masterthesisbe.dto.discussion.UpdateDiscussionRequestDto;
import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import com.example.masterthesisbe.model.Discussion;
import com.example.masterthesisbe.model.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class DiscussionMapper {
    private final UserProfileMapper userProfileMapper;

    public DiscussionResponseDto convertToResponseDto(Discussion discussion) {
        if (isNull(discussion)) {
            return null;
        }

        UserProfile author = discussion.getAuthor();
        UserProfileReferenceResponseDto convertedAuthor = userProfileMapper.convertToReferenceResponseDto(author);

        return new DiscussionResponseDto(
                discussion.getId(),
                discussion.getTitle(),
                discussion.getContent(),
                convertedAuthor,
                0,
                discussion.getCreatedOn(),
                discussion.getLastUpdatedOn()
        );
    }

    public Discussion convertFromCreateRequestDto(CreateDiscussionRequestDto newDiscussion) {
        if(isNull(newDiscussion)) {
            return null;
        }

        return new Discussion(
                newDiscussion.getTitle(),
                newDiscussion.getContent()
        );
    }

    public void updateDiscussionWithDto(Discussion discussion, UpdateDiscussionRequestDto discussionRequestDto) {
        discussion.setTitle(discussionRequestDto.getTitle());
        discussion.setContent(discussionRequestDto.getContent());
    }
}
