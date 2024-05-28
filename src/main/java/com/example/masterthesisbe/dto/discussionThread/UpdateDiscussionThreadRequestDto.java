package com.example.masterthesisbe.dto.discussionThread;

import com.example.masterthesisbe.constants.DiscussionThreadConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDiscussionThreadRequestDto {
    @NotBlank(message = DiscussionThreadConstants.CONTENT_NOT_BLANK_CONSTRAINT_MESSAGE)
    @Max(value = 1000, message = DiscussionThreadConstants.CONTENT_MAX_LENGTH_CONSTRAINT_MESSAGE)
    private String content;
}
