package com.example.masterthesisbe.dto.discussionThread;

import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import lombok.*;

import java.sql.Timestamp;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionThreadWithCommCountResponseDto {
    private long id;
    private String content;
    private UserProfileReferenceResponseDto author;
    private int commentsCount;
    private Timestamp createdOn;
    private Timestamp lastUpdatedOn;
}
