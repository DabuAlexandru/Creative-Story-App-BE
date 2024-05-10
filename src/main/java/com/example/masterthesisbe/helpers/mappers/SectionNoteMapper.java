package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.genre.CreateGenreRequestDto;
import com.example.masterthesisbe.dto.genre.GenreResponseDto;
import com.example.masterthesisbe.dto.genre.UpdateGenreRequestDto;
import com.example.masterthesisbe.model.Genre;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class SectionNoteMapper {

    public GenreResponseDto convertToResponseDto(Genre genre) {
        if (isNull(genre)) {
            return null;
        }

        return new GenreResponseDto(
                genre.getId(),
                genre.getName(),
                genre.getBadgeColor(),
                genre.getFontColor()
        );
    }

    public Genre convertFromCreateRequestDto(CreateGenreRequestDto newGenre) {
        if (isNull(newGenre)) {
            return null;
        }

        return new Genre(
                newGenre.getName(),
                newGenre.getBadgeColor(),
                newGenre.getFontColor()
        );
    }

    public void updateGenreWithDto(Genre genre, UpdateGenreRequestDto genreDto) {
        genre.setName(genreDto.getName());
        genre.setBadgeColor(genreDto.getBadgeColor());
        genre.setFontColor(genreDto.getFontColor());
    }
}
