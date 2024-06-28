package com.example.masterthesisbe.dto.discussion;

import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import lombok.*;

import java.sql.Timestamp;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionResponseDto {
    private long id;
    private String title;
    private String content;
    private UserProfileReferenceResponseDto author;
    private int commentsCount;
    private int voteValue;
    private byte userVote;
    private Timestamp createdOn;
    private Timestamp lastUpdatedOn;
}
