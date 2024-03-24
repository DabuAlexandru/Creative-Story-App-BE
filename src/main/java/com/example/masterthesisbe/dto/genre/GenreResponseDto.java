package com.example.masterthesisbe.dto.genre;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreResponseDto {
    private long id;
    private String name;
    private String color;
}
