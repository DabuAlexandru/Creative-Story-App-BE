package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.constants.SectionNoteConstants;
import com.example.masterthesisbe.dto.sectionNote.*;
import com.example.masterthesisbe.helpers.handlers.ValidationHandler;
import com.example.masterthesisbe.service.SectionNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/section-note")
@RequiredArgsConstructor
public class SectionNoteController extends ValidationHandler {
    private final SectionNoteService sectionNoteService;

    @GetMapping("/get-all/of-section/paginate/{sectionId}")
    public ResponseEntity<Page<SectionNoteResponseDto>> retrieveAllSectionNotesPaginate(
            @PathVariable int sectionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok().body(this.sectionNoteService.getSectionNotesPaginate(sectionId, page, size, sortBy));
    }

    @GetMapping("/get-all/of-section/{sectionId}")
    public ResponseEntity<List<SectionNoteResponseDto>> retrieveAllSectionNotes(@PathVariable int sectionId) {
        return ResponseEntity.ok().body(this.sectionNoteService.getAllNotesOfSection(sectionId));
    }

    @PostMapping("/create/for-section/{sectionId}")
    public ResponseEntity<SectionNoteResponseDto> createNewSectionNote(@PathVariable Integer sectionId, @Valid @RequestBody SectionNoteRequestDto newSectionNote) {
        return ResponseEntity.ok().body(this.sectionNoteService.createNewSectionNote(newSectionNote, sectionId));
    }

    @PutMapping("/update/{sectionNoteId}")
    public ResponseEntity<SectionNoteResponseDto> updateSectionNote(@PathVariable Integer sectionNoteId, @Valid @RequestBody SectionNoteRequestDto updatedSectionNote) {
        return ResponseEntity.ok().body(this.sectionNoteService.updateSectionNote(sectionNoteId, updatedSectionNote));
    }

    @DeleteMapping("/delete/{sectionNoteId}")
    public ResponseEntity<String> deleteSectionNote(@PathVariable Integer sectionNoteId) {
        this.sectionNoteService.deleteSectionNote(sectionNoteId);
        return ResponseEntity.ok().body(SectionNoteConstants.DELETE_OK_MESSAGE);
    }
}
