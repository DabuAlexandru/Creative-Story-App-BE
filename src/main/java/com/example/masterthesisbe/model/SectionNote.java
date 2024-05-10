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
@Table(name = "section")
public class SectionNote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	private Timestamp creationDate;
	private Timestamp lastModifiedDate;

	@ManyToOne
	@JoinColumn(name = "section_id")
	private Section section;

	public SectionNote(String title, String content) {
		this.title = title;
		this.content = content;
	}
}