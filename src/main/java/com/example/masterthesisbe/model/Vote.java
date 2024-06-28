package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vote_value", nullable = false)
    private Byte voteValue; // 1 for upvote, -1 for downvote

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    public Vote(UserProfile userProfile, Byte voteValue) {
        this.userProfile = userProfile;
        this.voteValue = voteValue;
    }
}
