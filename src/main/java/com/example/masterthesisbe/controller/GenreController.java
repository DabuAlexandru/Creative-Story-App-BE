package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.dto.genre.CreateGenreRequestDto;
import com.example.masterthesisbe.dto.genre.GenreResponseDto;
import com.example.masterthesisbe.dto.genre.UpdateGenreRequestDto;
import com.example.masterthesisbe.helpers.handlers.ValidationHandler;
import com.example.masterthesisbe.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/genre")
public class GenreController extends ValidationHandler {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<GenreResponseDto>> retrieveAllGenres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok().body(this.genreService.getGenresPaginate(page, size, sortBy));
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<GenreResponseDto> retrieveGenre(@PathVariable Integer genreId) {
        return ResponseEntity.ok().body(this.genreService.getGenreById(genreId));
    }

    @PostMapping("/create")
    public ResponseEntity<GenreResponseDto> createNewGenre(@Valid @RequestBody CreateGenreRequestDto newGenre) {
        return ResponseEntity.ok().body(this.genreService.createNewGenre(newGenre));
    }

    @PutMapping("/update/{genreId}")
    public ResponseEntity<GenreResponseDto> updateGenre(@PathVariable Integer genreId, @Valid @RequestBody UpdateGenreRequestDto updatedGenre) {
        return ResponseEntity.ok().body(this.genreService.updateGenre(genreId, updatedGenre));
    }
}
