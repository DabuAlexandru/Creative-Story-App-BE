package com.example.masterthesisbe.dto.story;

import com.example.masterthesisbe.dto.auth.UserDto;
import com.example.masterthesisbe.dto.genre.GenreResponseDto;
import com.example.masterthesisbe.model.Genre;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponseDto {
    private long id;
    private String title;
    private String description;
    private Instant createdOn;
    private Instant lastUpdatedOn;
    private Set<GenreResponseDto> genres;
    private UserDto author;
}
