package com.example.masterthesisbe.dto.userProfile;

import lombok.*;

import java.time.Instant;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryReadLaterResponseDto {
    private int storyId;
    private Instant createdOn;
}
