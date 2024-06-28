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
public class StoryReviewWithVotesResponseDto extends StoryReviewResponseDto {
    private int voteValue;
    private byte userVote;

    public StoryReviewWithVotesResponseDto(long id, String title, String content, int characterScore, int conflictScore, int plotScore, int settingScore, int themeScore, Timestamp createdOn, UserProfileReferenceResponseDto userProfile, int voteValue, byte userVote) {
        super(id, title, content, characterScore, conflictScore, plotScore, settingScore, themeScore, createdOn, userProfile);
        this.voteValue = voteValue;
        this.userVote = userVote;
    }
}
