package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.constants.StoryConstants;
import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoryContentRequestDto {
    private String content;
}
