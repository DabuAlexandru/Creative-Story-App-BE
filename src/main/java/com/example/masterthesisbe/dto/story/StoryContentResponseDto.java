package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import com.example.masterthesisbe.dto.userProfile.UserProfileResponseDto;
import lombok.*;

import java.time.Instant;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryContentResponseDto {
    private long id;
    private String title;
    private String content;
    private Instant createdOn;
    private Instant lastUpdatedOn;
//    private Set<Genre> genres;
    private UserProfileReferenceResponseDto author;
}
