package com.example.masterthesisbe.dto.userProfile;

import com.example.masterthesisbe.dto.fileInstance.FileInstanceResponseDto;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponseDto {
    private int id;
    private String penName;
    private String headline;
    private String fullName;
    private String bio;
    private String location;
    private String website;
    private Set<StoryFavoriteResponseDto> favorites;
    private Set<StoryReadLaterResponseDto> readingLists;
    private FileInstanceResponseDto profilePicture;

    public UserProfileResponseDto(int id, String penName, String headline, String fullName, String bio, String location, String website, FileInstanceResponseDto profilePicture) {
        this.id = id;
        this.penName = penName;
        this.headline = headline;
        this.fullName = fullName;
        this.bio = bio;
        this.location = location;
        this.website = website;
        this.profilePicture = profilePicture;
        this.favorites = new HashSet<>();
        this.readingLists = new HashSet<>();
    }
}
