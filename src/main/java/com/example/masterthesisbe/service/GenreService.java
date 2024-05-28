package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.GenreConstants;
import com.example.masterthesisbe.dto.genre.CreateGenreRequestDto;
import com.example.masterthesisbe.dto.genre.GenreDictionaryResponseDto;
import com.example.masterthesisbe.dto.genre.GenreResponseDto;
import com.example.masterthesisbe.dto.genre.UpdateGenreRequestDto;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.helpers.mappers.GenreMapper;
import com.example.masterthesisbe.model.Genre;
import com.example.masterthesisbe.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public List<GenreDictionaryResponseDto> getGenreDictionaries() {
        List<Genre> mainGenres = genreRepository.findAllByMainGenreIdOrderByName(null);

        return mainGenres.stream()
                .map(this::getGenreDictionary)
                .collect(Collectors.toList());
    }

    private GenreDictionaryResponseDto getGenreDictionary(Genre genre) {
        List<GenreResponseDto> subGenres = getGenres(genre.getId());
        GenreDictionaryResponseDto genreDict = genreMapper.convertToDictionaryResponseDto(genre);
        genreDict.setSubGenres(subGenres);
        return genreDict;
    }

    public List<GenreResponseDto> getGenres(Integer mainGenreId) {
        List<Genre> subGenres = genreRepository.findAllByMainGenreIdOrderByName(mainGenreId);
        return subGenres.stream()
                .map(genreMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public GenreResponseDto getGenreById(Integer genreId) {
        Genre foundGenre = this.genreRepository.findById(genreId)
                .orElseThrow(() -> new ApiException(GenreConstants.GENRE_NOT_FOUND_MESSAGE));
        return genreMapper.convertToResponseDto(foundGenre);
    }

    public GenreResponseDto createNewGenre(CreateGenreRequestDto genre) {
        Genre convertedGenre = genreMapper.convertFromCreateRequestDto(genre);
        if (!isNull(genre.getMainGenreId())) {
            Genre mainGenre = genreRepository.findById(genre.getMainGenreId())
                    .orElseThrow(() -> new ApiException(GenreConstants.GENRE_NOT_FOUND_MESSAGE));
            convertedGenre.setMainGenre(mainGenre);
        }
        Genre newGenre = genreRepository.save(convertedGenre);
        return genreMapper.convertToResponseDto(newGenre);
    }

    public GenreResponseDto updateGenre(Integer genreId, UpdateGenreRequestDto updatedGenre) {
        Genre genre = this.genreRepository.findById(genreId)
                .orElseThrow(() -> new ApiException(GenreConstants.GENRE_NOT_FOUND_MESSAGE));

        genreMapper.updateGenreWithDto(genre, updatedGenre);
        return this.genreMapper.convertToResponseDto(genreRepository.save(genre));
    }
}
