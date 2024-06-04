package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "genre",
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueGenreName", columnNames = {"name"})
        })
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @CreatedDate
    private Timestamp createdOn;
    @LastModifiedDate
    private Timestamp lastUpdatedOn;
    @CreatedBy
    private long createdBy;
    @LastModifiedBy
    private long lastModifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    Genre mainGenre;

    @ManyToMany(mappedBy = "genres")
    Set<Story> stories;

    public Genre(String name) {
        this.name = name;
    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
}