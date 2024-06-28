package com.example.masterthesisbe.dto.section;

import com.example.masterthesisbe.constants.SectionConstants;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSectionWithRefRequestDto extends UpdateSectionRequestDto {
    @NotNull(message = SectionConstants.SECTION_NOT_NULL_CONSTRAINT_MESSAGE)
    private Integer id;

    @NotNull(message = SectionConstants.ORDER_NOT_NULL_CONSTRAINT_MESSAGE)
    private Integer displayOrder;
}
