package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String penName;
    private String headline;
    private String fullName;
    private String bio;
    private String location;
    private String website;

    @OneToOne
    @JoinColumn(name = "profile_picture_id")
    private FileInstance profilePicture;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StoryFavorite> favorites = new HashSet<>();

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StoryReadLater> readingLists = new HashSet<>();
}
