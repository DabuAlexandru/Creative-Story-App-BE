package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.section.*;
import com.example.masterthesisbe.model.Section;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Objects.isNull;
import java.sql.Timestamp;

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

    public SectionWithContentResponseDto convertToResponseWithContentDto(Map<String, Object> queryResult) {
        if (isNull(queryResult)) {
            return null;
        }

        int id = (int) queryResult.get("id");
        String title = (String) queryResult.get("title");
        int displayOrder = (int) queryResult.get("display_order");
        String content = (String) queryResult.get("content");
        Timestamp creationDate = (Timestamp) queryResult.get("creation_date");
        Timestamp lastModifiedDate = (Timestamp) queryResult.get("last_modified_date");

        return new SectionWithContentResponseDto(
                id,
                title,
                displayOrder,
                content,
                creationDate,
                lastModifiedDate
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

    public void updateSectionWithDto(Section section, UpdateSectionWithRefRequestDto sectionDto) {
        section.setTitle(sectionDto.getTitle());
        section.setDisplayOrder(sectionDto.getDisplayOrder());
    }
}
