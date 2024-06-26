package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.dto.genre.CreateGenreRequestDto;
import com.example.masterthesisbe.dto.genre.GenreDictionaryResponseDto;
import com.example.masterthesisbe.dto.genre.GenreResponseDto;
import com.example.masterthesisbe.dto.genre.UpdateGenreRequestDto;
import com.example.masterthesisbe.helpers.handlers.ValidationHandler;
import com.example.masterthesisbe.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/genre")
@RequiredArgsConstructor
public class GenreController extends ValidationHandler {
    private final GenreService genreService;

    @GetMapping("/get-dict")
    public ResponseEntity<List<GenreDictionaryResponseDto>> retrieveAllGenreDictionaries() {
        return ResponseEntity.ok().body(this.genreService.getGenreDictionaries());
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<GenreResponseDto>> retrieveAllGenres() {
        return ResponseEntity.ok().body(this.genreService.getAllGenres());
    }

    @GetMapping("/get-main")
    public ResponseEntity<List<GenreResponseDto>> retrieveAllMainGenres() {
        return ResponseEntity.ok().body(this.genreService.getGenres(null));
    }

    @GetMapping("/get-sub/{mainGenreId}")
    public ResponseEntity<List<GenreResponseDto>> retrieveAllSubGenresOfMain(@PathVariable Integer mainGenreId) {
        return ResponseEntity.ok().body(this.genreService.getGenres(mainGenreId));
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
