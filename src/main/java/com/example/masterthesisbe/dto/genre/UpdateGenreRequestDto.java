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
public class UpdateGenreRequestDto {
    @NotBlank(message = GenreConstants.NAME_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String name;
}
