package com.example.masterthesisbe.dto.discussion;

import com.example.masterthesisbe.constants.DiscussionConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiscussionRequestDto {
    @NotBlank(message = DiscussionConstants.TITLE_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String title;

    @NotBlank(message = DiscussionConstants.CONTENT_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String content;
}
