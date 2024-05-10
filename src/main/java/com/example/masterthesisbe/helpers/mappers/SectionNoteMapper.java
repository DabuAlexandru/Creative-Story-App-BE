package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.sectionNote.SectionNoteRequestDto;
import com.example.masterthesisbe.dto.sectionNote.SectionNoteResponseDto;
import com.example.masterthesisbe.model.SectionNote;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class SectionNoteMapper {

    public SectionNoteResponseDto convertToResponseDto(SectionNote sectionNote) {
        if (isNull(sectionNote)) {
            return null;
        }

        return new SectionNoteResponseDto(
                sectionNote.getId(),
                sectionNote.getTitle(),
                sectionNote.getContent(),
                sectionNote.getCreationDate(),
                sectionNote.getLastModifiedDate()
        );
    }

    public SectionNote convertFromRequestDto(SectionNoteRequestDto sectionNote) {
        if (isNull(sectionNote)) {
            return null;
        }

        return new SectionNote(
                sectionNote.getTitle(),
                sectionNote.getContent()
        );
    }

    public void updateSectionNoteWithDto(SectionNote sectionNote, SectionNoteRequestDto sectionNoteDto) {
        sectionNote.setTitle(sectionNoteDto.getTitle());
        sectionNote.setContent(sectionNoteDto.getContent());
    }
}
