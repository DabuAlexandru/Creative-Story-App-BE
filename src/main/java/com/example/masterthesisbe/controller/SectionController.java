package com.example.masterthesisbe.controller;

import com.example.masterthesisbe.constants.SectionConstants;
import com.example.masterthesisbe.dto.section.*;
import com.example.masterthesisbe.helpers.handlers.ValidationHandler;
import com.example.masterthesisbe.service.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/section")
@RequiredArgsConstructor
public class SectionController extends ValidationHandler {
    private final SectionService sectionService;

    @GetMapping("/get-all/of-story/paginate/{storyId}")
    public ResponseEntity<Page<SectionResponseDto>> retrieveAllSectionsPaginate(
            @PathVariable Integer storyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok().body(this.sectionService.getSectionsPaginate(storyId, page, size, sortBy));
    }

    @GetMapping("/get-all/of-story/{storyId}")
    public ResponseEntity<List<SectionResponseDto>> retrieveAllSections(@PathVariable Integer storyId) {
        return ResponseEntity.ok().body(this.sectionService.getAllSectionsOfStory(storyId));
    }

    @GetMapping("/get/content/{sectionId}")
    public ResponseEntity<SectionContentResponseDto> retrieveSection(@PathVariable Integer sectionId) {
        return ResponseEntity.ok().body(this.sectionService.getSectionContentById(sectionId));
    }

    @PostMapping("/create/for-story/{storyId}")
    public ResponseEntity<SectionResponseDto> createNewSection(@PathVariable Integer storyId, @Valid @RequestBody CreateSectionRequestDto newSection) {
        return ResponseEntity.ok().body(this.sectionService.createNewSection(newSection, storyId));
    }

    @PutMapping("/update/{sectionId}")
    public ResponseEntity<SectionResponseDto> updateSection(@PathVariable Integer sectionId, @Valid @RequestBody UpdateSectionRequestDto updatedSection) {
        return ResponseEntity.ok().body(this.sectionService.updateSection(sectionId, updatedSection));
    }

    @PutMapping("/update/content/{sectionId}")
    public ResponseEntity<String> updateSectionContent(@PathVariable Integer sectionId, @Valid @RequestBody UpdateSectionContentRequestDto updatedSection) {
        this.sectionService.updateSectionContent(sectionId, updatedSection);
        return ResponseEntity.ok().body(SectionConstants.SECTION_UPDATE_CONTENT);
    }

    @DeleteMapping("/delete/{sectionId}")
    public ResponseEntity<String> deleteSection(@PathVariable Integer sectionId) {
        this.sectionService.deleteSection(sectionId);
        return ResponseEntity.ok().body(SectionConstants.DELETE_OK_MESSAGE);
    }
}
