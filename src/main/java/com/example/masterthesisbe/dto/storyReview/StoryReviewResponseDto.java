package com.example.masterthesisbe.dto.storyReview;

import com.example.masterthesisbe.dto.userProfile.UserProfileReferenceResponseDto;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;

@Validated
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryReviewResponseDto {
    private long id;
    private String title;
    private String content;

    private int characterScore;
    private int conflictScore;
    private int plotScore;
    private int settingScore;
    private int themeScore;

    private Timestamp createdOn;
    private UserProfileReferenceResponseDto userProfile;
}
