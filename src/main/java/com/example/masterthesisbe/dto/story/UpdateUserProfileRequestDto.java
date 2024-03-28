package com.example.masterthesisbe.dto.story;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileRequestDto {
    private String fullName;
    private String bio;
    private String location;
    private String website;
}
