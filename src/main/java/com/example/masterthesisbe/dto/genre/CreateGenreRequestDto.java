package com.example.masterthesisbe.dto.genre;

import com.example.masterthesisbe.constants.GenreConstants;
import com.example.masterthesisbe.model.Genre;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
            regexp="^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$",
            message = GenreConstants.COLOR_WRONG_FORMAT_CONSTRAINT_MESSAGE
    )
    private String color;
}
