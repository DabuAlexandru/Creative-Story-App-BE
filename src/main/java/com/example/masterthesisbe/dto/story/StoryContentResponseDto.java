package com.example.masterthesisbe.dto.story;

import lombok.*;

import java.sql.Timestamp;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryContentResponseDto {
    private long id;
    private String title;
    private String content;
    private Timestamp creationDate;
    private Timestamp lastModifiedDate;
}
