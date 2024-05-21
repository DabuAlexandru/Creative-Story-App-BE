package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.genre.*;
import com.example.masterthesisbe.model.Genre;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static java.util.Objects.isNull;

@Component
public class GenreMapper {

    public GenreResponseDto convertToResponseDto(Genre genre) {
        if (isNull(genre)) {
            return null;
        }
        Integer mainGenreId = isNull(genre.getMainGenre()) ? null : genre.getMainGenre().getId();

        return new GenreResponseDto(
                genre.getId(),
                genre.getName(),
                mainGenreId
        );
    }

    public Genre convertFromDto(GenreDto genreDto) {
        if (isNull(genreDto)) {
            return null;
        }

        return new Genre(
            genreDto.getId(), genreDto.getName()
        );
    }

    public GenreDictionaryResponseDto convertToDictionaryResponseDto(Genre genre) {
        if (isNull(genre)) {
            return null;
        }

        return new GenreDictionaryResponseDto(
                genre.getId(),
                genre.getName(),
                new ArrayList<>()
        );
    }

    public Genre convertFromCreateRequestDto(CreateGenreRequestDto newGenre) {
        if (isNull(newGenre)) {
            return null;
        }

        return new Genre(
                newGenre.getName()
        );
    }

    public void updateGenreWithDto(Genre genre, UpdateGenreRequestDto genreDto) {
        genre.setName(genreDto.getName());
    }
}
