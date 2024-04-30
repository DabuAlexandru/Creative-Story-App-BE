package com.example.masterthesisbe.dto.story;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryOverallScoreResponseDto {
    private int numOfReviews = 0;
    private double characterScore = 0;
    private double conflictScore = 0;
    private double plotScore = 0;
    private double settingScore = 0;
    private double themeScore = 0;
}
