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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "discussionThread")
public class DiscussionThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "varchar(1023)")
    private String content;

    @ManyToOne
    @JoinColumn(name="author_id")
    private UserProfile author;

    @ManyToOne
    @JoinColumn(name="discussion_id")
    private Discussion discussion;

    @CreatedDate
    private Timestamp createdOn;
    @LastModifiedDate
    private Timestamp lastUpdatedOn;
    @CreatedBy
    private long createdBy;
    @LastModifiedBy
    private long lastModifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="main_thread_id")
    DiscussionThread mainThread;

    public DiscussionThread(String content) {
        this.content = content;
    }
}