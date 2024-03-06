package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.constants.StoryConstants;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponseDto {
    private long id;
    private String title;
    private String description;
    private Timestamp creationDate;
    private Timestamp lastModifiedDate;
}
