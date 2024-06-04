package com.example.masterthesisbe.dto.genre;

import com.example.masterthesisbe.constants.GenreConstants;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Validated
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGenreRequestDto {
    @NotBlank(message = GenreConstants.NAME_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String name;
    private Integer mainGenreId;
}
