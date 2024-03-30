package com.example.masterthesisbe.dto.userProfile;

import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileReducedResponseDto {
    private String fullName;
    private String bio;
    private String location;
    private String website;
    private Set<Integer> favorites;
    private Set<Integer> readingLists;
}
