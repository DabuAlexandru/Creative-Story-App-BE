package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.genre.CreateGenreRequestDto;
import com.example.masterthesisbe.dto.genre.GenreResponseDto;
import com.example.masterthesisbe.dto.genre.UpdateGenreRequestDto;
import com.example.masterthesisbe.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class GenreMapper {

    public GenreResponseDto convertToResponseDto(Genre genre) {
        if (isNull(genre)) {
            return null;
        }

        return new GenreResponseDto(
                genre.getId(),
                genre.getName(),
                genre.getColor()
        );
    }

    public Genre convertFromCreateRequestDto(CreateGenreRequestDto newGenre) {
        if (isNull(newGenre)) {
            return null;
        }

        return new Genre(
                newGenre.getName(),
                newGenre.getColor()
        );
    }

    public void updateGenreWithDto(Genre genre, UpdateGenreRequestDto genreDto) {
        genre.setName(genreDto.getName());
        genre.setColor(genreDto.getColor());
    }
}
