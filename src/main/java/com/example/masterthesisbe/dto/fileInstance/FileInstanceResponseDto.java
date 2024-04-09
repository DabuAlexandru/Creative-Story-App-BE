package com.example.masterthesisbe.dto.fileInstance;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileInstanceResponseDto {
    private String fileName;
    private int userId;
}
