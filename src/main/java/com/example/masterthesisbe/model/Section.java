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
@Table(name = "section",
		uniqueConstraints = {
			@UniqueConstraint(
					name = "SectionTitleStoryUnique",
					columnNames = {"story_id", "title"}
			)
})
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;

	private int displayOrder;

	@CreatedDate
	private Timestamp creationDate;
	@LastModifiedDate
	private Timestamp lastModifiedDate;

	@ManyToOne
	@JoinColumn(name = "story_id")
	private Story story;

	public Section(String title) {
		this.title = title;
	}
}