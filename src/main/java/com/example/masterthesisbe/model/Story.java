package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    private Timestamp creationDate;
    private Timestamp lastModifiedDate;
    private long userId;
    // temporary integration - will be replaced
    private String content;

    public Story(String title, String description, Timestamp creationDate, Timestamp lastModifiedDate) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}