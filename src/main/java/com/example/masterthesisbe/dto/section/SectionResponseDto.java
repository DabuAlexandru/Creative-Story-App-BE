package com.example.masterthesisbe.dto.section;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectionResponseDto {
    private int id;
    private String title;
    private int displayOrder;
    private Timestamp createdOn;
    private Timestamp lastUpdatedOn;
}
