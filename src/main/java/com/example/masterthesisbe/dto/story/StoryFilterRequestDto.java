package com.example.masterthesisbe.dto.story;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryFilterRequestDto {
    private String title;
    private List<Integer> authorIds;
    private List<Integer> genreIds;
}
