package com.example.masterthesisbe.dto.userProfile;

import lombok.*;

import java.sql.Timestamp;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryFavoriteResponseDto {
    private int storyId;
    private Timestamp createdOn;
}
