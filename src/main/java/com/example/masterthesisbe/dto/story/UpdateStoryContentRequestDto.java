package com.example.masterthesisbe.dto.story;

import lombok.*;

import org.springframework.validation.annotation.Validated;

@Validated
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoryContentRequestDto {
    private String content;
}
