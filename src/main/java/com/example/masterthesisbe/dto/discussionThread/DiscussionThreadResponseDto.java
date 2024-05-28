package com.example.masterthesisbe.dto.discussionThread;

import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import lombok.*;

import java.time.Instant;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionThreadResponseDto {
    private long id;
    private String content;
    private UserProfileReferenceResponseDto author;
    private Instant createdOn;
    private Instant lastUpdatedOn;
}
