package com.example.masterthesisbe.dto.genre;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreDictionaryResponseDto {
    private long id;
    private String name;
    private List<GenreResponseDto> subGenres;
}
