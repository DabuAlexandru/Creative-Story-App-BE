package com.example.masterthesisbe.dto.section;

import com.example.masterthesisbe.constants.SectionConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSectionRequestDto {
    @NotBlank(message = SectionConstants.TITLE_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String title;

    private String summary;
}
