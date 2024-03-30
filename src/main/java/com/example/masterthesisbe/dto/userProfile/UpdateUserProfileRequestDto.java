package com.example.masterthesisbe.dto.userProfile;

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
