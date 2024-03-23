package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

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

    // temporary integration - will be replaced
    private String content;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    public Story(String title, String description) {
        this.title = title;
        this.description = description;
    }
}