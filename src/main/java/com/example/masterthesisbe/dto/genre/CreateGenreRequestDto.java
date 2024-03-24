package com.example.masterthesisbe.dto.genre;

import com.example.masterthesisbe.constants.GenreConstants;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Validated
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGenreRequestDto {
    @NotBlank(message = GenreConstants.NAME_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String name;

    @NotBlank(message = GenreConstants.COLOR_NOT_BLANK_CONSTRAINT_MESSAGE)
    @Pattern(
            regexp="#([\\da-fA-F]{2})([\\da-fA-F]{2})([\\da-fA-F]{2})",
            message = GenreConstants.COLOR_WRONG_FORMAT_CONSTRAINT_MESSAGE
    )
    private String badgeColor;

    @NotBlank(message = GenreConstants.COLOR_NOT_BLANK_CONSTRAINT_MESSAGE)
    @Pattern(
            regexp="#([\\da-fA-F]{2})([\\da-fA-F]{2})([\\da-fA-F]{2})",
            message = GenreConstants.COLOR_WRONG_FORMAT_CONSTRAINT_MESSAGE
    )
    private String fontColor;
}
