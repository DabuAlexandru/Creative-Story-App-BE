package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sectionContent")
public class SectionContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(columnDefinition = "TEXT")
	private String content;

	@CreationTimestamp
	private Timestamp creationDate;
	@UpdateTimestamp
	private Timestamp lastModifiedDate;

	@OneToOne
	@JoinColumn(name = "section_id")
	private Section section;

	public SectionContent(String content) {
		this.content = content;
	}
}