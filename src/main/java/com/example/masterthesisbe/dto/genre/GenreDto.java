package com.example.masterthesisbe.dto.genre;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    private int id;
    private String name;
    private Integer mainGenreId;
}
