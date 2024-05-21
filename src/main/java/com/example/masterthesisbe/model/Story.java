package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "story",
        uniqueConstraints = {
                @UniqueConstraint(name = "AuthorTitleStoryUnique", columnNames = {"userId", "title"})
        })
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;

    @CreationTimestamp
    private Instant createdOn;
    @UpdateTimestamp
    private Instant lastUpdatedOn;

    @ManyToMany
    @JoinTable(
            name = "story_genre",
            joinColumns = @JoinColumn(name = "story_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @Column(columnDefinition = "TEXT")
    private String preview;

    @ManyToOne
    @JoinColumn(name="author_id")
    private UserProfile author;

    @OneToOne
    @JoinColumn(name = "story_overall_score_id")
    private StoryOverallScore storyOverallScore;

    @OneToOne
    @JoinColumn(name = "cover_picture_id")
    private FileInstance coverPicture;

    public Story(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }
}