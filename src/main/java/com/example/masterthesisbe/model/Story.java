package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "story",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "AuthorTitleStoryUnique",
                        columnNames = {"author_id", "title"}
                )
        })
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(columnDefinition = "varchar(1020)")
    private String description;

    @CreatedDate
    private Timestamp createdOn;
    @LastModifiedDate
    private Timestamp lastUpdatedOn;

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