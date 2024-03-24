package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.GenreConstants;
import com.example.masterthesisbe.dto.genre.CreateGenreRequestDto;
import com.example.masterthesisbe.dto.genre.GenreResponseDto;
import com.example.masterthesisbe.dto.genre.UpdateGenreRequestDto;
import com.example.masterthesisbe.helpers.mappers.GenreMapper;
import com.example.masterthesisbe.model.Genre;
import com.example.masterthesisbe.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public Page<GenreResponseDto> getGenresPaginate(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Genre> genresPagination = this.genreRepository.findAll(pageable);

        List<GenreResponseDto> content = genresPagination.getContent().stream()
                .map(genreMapper::convertToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, genresPagination.getPageable(), genresPagination.getTotalElements());
    }

    public GenreResponseDto getGenreById(Integer genreId) {
        Genre foundGenre = this.genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException(GenreConstants.GENRE_NOT_FOUND_MESSAGE));
        return genreMapper.convertToResponseDto(foundGenre);
    }

    public GenreResponseDto createNewGenre(CreateGenreRequestDto genre) {
        Genre convertedGenre = genreMapper.convertFromCreateRequestDto(genre);
        Genre newGenre = genreRepository.save(convertedGenre);
        return genreMapper.convertToResponseDto(newGenre);
    }

    public GenreResponseDto updateGenre(Integer genreId, UpdateGenreRequestDto updatedGenre) {
        Genre genre = this.genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException(GenreConstants.GENRE_NOT_FOUND_MESSAGE));

        genreMapper.updateGenreWithDto(genre, updatedGenre);
        return this.genreMapper.convertToResponseDto(genreRepository.save(genre));
    }
}
