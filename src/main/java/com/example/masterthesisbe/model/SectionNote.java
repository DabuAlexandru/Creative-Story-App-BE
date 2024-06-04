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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sectionNote")
public class SectionNote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	@CreatedDate
	private Timestamp creationDate;
	@LastModifiedDate
	private Timestamp lastModifiedDate;

	@ManyToOne
	@JoinColumn(name = "section_id")
	private Section section;

	public SectionNote(String title, String content) {
		this.title = title;
		this.content = content;
	}
}