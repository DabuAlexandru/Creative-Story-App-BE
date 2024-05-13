package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.section.CreateSectionRequestDto;
import com.example.masterthesisbe.dto.section.SectionContentResponseDto;
import com.example.masterthesisbe.dto.section.SectionResponseDto;
import com.example.masterthesisbe.dto.section.UpdateSectionRequestDto;
import com.example.masterthesisbe.model.Section;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class SectionMapper {

    public SectionResponseDto convertToResponseDto(Section section) {
        if (isNull(section)) {
            return null;
        }

        return new SectionResponseDto(
                section.getId(),
                section.getTitle(),
                section.getDisplayOrder(),
                section.getCreationDate(),
                section.getLastModifiedDate()
        );
    }

    public Section convertFromCreateRequestDto(CreateSectionRequestDto newSection) {
        if (isNull(newSection)) {
            return null;
        }

        return new Section(
                newSection.getTitle()
        );
    }

    public void updateSectionWithDto(Section section, UpdateSectionRequestDto sectionDto) {
        section.setTitle(sectionDto.getTitle());
    }
}
