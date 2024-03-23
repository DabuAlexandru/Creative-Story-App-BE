package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.dto.auth.UserDto;
import com.example.masterthesisbe.model.Genre;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

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
//    private Set<Genre> genres;
    private UserDto author;
}
