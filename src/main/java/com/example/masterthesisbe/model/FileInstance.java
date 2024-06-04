package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "file_instance")
public class FileInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fileName;

    public FileInstance(String fileName, User user) {
        this.fileName = fileName;
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @CreatedDate
    private Timestamp createdOn;
    @CreatedBy
    private long createdBy;
}
