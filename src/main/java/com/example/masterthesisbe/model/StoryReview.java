package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "story_review",
        uniqueConstraints = {
                @UniqueConstraint(name = "NoReviewDuplicates", columnNames = {"story_id", "user_profile_id"})
        })
public class StoryReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    private Instant createdOn;

    private String title;
    private String content;
    private int completionPercentage;

    private int characterScore;
    private int conflictScore;
    private int plotScore;
    private int settingScore;
    private int themeScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    private Story story;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    public StoryReview(String title, String content, int completionPercentage, int characterScore, int conflictScore, int plotScore, int settingScore, int themeScore) {
        this.title = title;
        this.content = content;
        this.completionPercentage = completionPercentage;
        this.characterScore = characterScore;
        this.conflictScore = conflictScore;
        this.plotScore = plotScore;
        this.settingScore = settingScore;
        this.themeScore = themeScore;
    }
}