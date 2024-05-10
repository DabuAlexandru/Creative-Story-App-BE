package com.example.masterthesisbe.dto.sectionNote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectionNoteResponseDto {
    private int id;
    private String title;
    private String content;
    private Timestamp createdOn;
    private Timestamp lastUpdatedOn;
}
