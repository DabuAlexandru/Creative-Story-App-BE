package com.example.masterthesisbe.dto.general;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountPaginateResponseDto {
    int totalElements;
    int totalPages;
}
