package com.example.masterthesisbe.dto.userProfile;

import com.example.masterthesisbe.dto.fileInstance.FileInstanceResponseDto;
import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileReferenceResponseDto {
    private int id;
    private String penName;
    private String headline;
    private String fullName;
    private String bio;
    private FileInstanceResponseDto profilePicture;
}
