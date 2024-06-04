package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import lombok.*;

import java.sql.Timestamp;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryContentResponseDto {
    private long id;
    private String title;
    private String content;
    private Timestamp createdOn;
    private Timestamp lastUpdatedOn;
//    private Set<Genre> genres;
    private UserProfileReferenceResponseDto author;
}
