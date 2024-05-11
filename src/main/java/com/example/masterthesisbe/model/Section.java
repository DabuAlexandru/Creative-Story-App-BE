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
@Table(name = "section",
		uniqueConstraints = {
		@UniqueConstraint(name = "SectionTitleStoryUnique", columnNames = {"storyId", "title"})
})
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;

	@Column(columnDefinition = "TEXT")
	private String summary;

	@CreationTimestamp
	private Timestamp creationDate;
	@UpdateTimestamp
	private Timestamp lastModifiedDate;

	@ManyToOne
	@JoinColumn(name = "story_id")
	private Story story;

	// temporary
	@Column(columnDefinition = "TEXT")
	private String content;

	public Section(String title, String summary) {
		this.title = title;
		this.summary = summary;
	}
}