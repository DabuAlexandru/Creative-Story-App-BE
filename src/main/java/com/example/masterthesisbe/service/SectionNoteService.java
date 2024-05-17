package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.SectionNoteConstants;
import com.example.masterthesisbe.constants.SectionConstants;
import com.example.masterthesisbe.dto.sectionNote.*;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.helpers.mappers.SectionNoteMapper;
import com.example.masterthesisbe.model.SectionNote;
import com.example.masterthesisbe.model.Section;
import com.example.masterthesisbe.model.User;
import com.example.masterthesisbe.repository.SectionNoteRepository;
import com.example.masterthesisbe.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionNoteService {
    private final SectionNoteRepository sectionNoteRepository;
    private final SectionNoteMapper sectionNoteMapper;
    private final SectionRepository sectionRepository;
    private final AuthenticationService authService;

    public Page<SectionNoteResponseDto> getSectionNotesPaginate(int sectionId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<SectionNote> sectionNotesPagination = this.sectionNoteRepository.findAllBySectionId(sectionId, pageable);

        List<SectionNoteResponseDto> content = sectionNotesPagination.getContent().stream()
                .map(sectionNoteMapper::convertToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, sectionNotesPagination.getPageable(), sectionNotesPagination.getTotalElements());
    }

    public List<SectionNoteResponseDto> getAllNotesOfSection(int sectionId) {
        return this.sectionNoteRepository.findAllBySectionIdOrderByLastModifiedDateDesc(sectionId).stream()
                .map(sectionNoteMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public SectionNoteResponseDto createNewSectionNote(SectionNoteRequestDto sectionNote, int sectionId) {
        SectionNote convertedSectionNote = sectionNoteMapper.convertFromRequestDto(sectionNote);

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiException(SectionConstants.SECTION_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if (loggedInUser.getId() != section.getStory().getAuthor().getId()) {
            throw new ApiException(SectionNoteConstants.NO_PERMISSIONS_TO_MODIFY);
        }
        convertedSectionNote.setSection(section);

        SectionNote newSectionNote = sectionNoteRepository.save(convertedSectionNote);
        return sectionNoteMapper.convertToResponseDto(newSectionNote);
    }

    public SectionNoteResponseDto updateSectionNote(Integer sectionNoteId, SectionNoteRequestDto updatedSectionNote) {
        SectionNote sectionNote = this.sectionNoteRepository.findById(sectionNoteId)
                .orElseThrow(() -> new ApiException(SectionConstants.SECTION_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if (loggedInUser.getId() != sectionNote.getSection().getStory().getAuthor().getId()) {
            throw new ApiException(SectionNoteConstants.NO_PERMISSIONS_TO_MODIFY);
        }

        sectionNoteMapper.updateSectionNoteWithDto(sectionNote, updatedSectionNote);
        return this.sectionNoteMapper.convertToResponseDto(sectionNoteRepository.save(sectionNote));
    }

    public void deleteSectionNote(Integer sectionNoteId) {
        boolean exists = this.sectionNoteRepository.existsById(sectionNoteId);

        if(!exists) {
            throw new ApiException(SectionConstants.SECTION_NOT_FOUND_MESSAGE);
        }

        this.sectionNoteRepository.deleteById(sectionNoteId);
    }
}
