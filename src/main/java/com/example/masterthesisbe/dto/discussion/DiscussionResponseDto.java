package com.example.masterthesisbe.dto.discussion;

import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import lombok.*;

import java.time.Instant;

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
    private Instant createdOn;
    private Instant lastUpdatedOn;
}
