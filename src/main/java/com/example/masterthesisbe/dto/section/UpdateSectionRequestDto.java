package com.example.masterthesisbe.dto.section;

import com.example.masterthesisbe.constants.SectionConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSectionRequestDto {
    @NotBlank(message = SectionConstants.TITLE_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String title;
}
