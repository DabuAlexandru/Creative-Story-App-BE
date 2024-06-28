package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "story_review_votes", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_profile_id", "review_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryReviewVote extends Vote {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id", nullable = false)
	private StoryReview review;

	public StoryReviewVote(UserProfile userProfile, StoryReview review, Byte voteValue) {
		super(userProfile, voteValue);
		this.review = review;
	}
}
