package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "discussion_votes", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_profile_id", "discussion_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionVote extends Vote {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "discussion_id", nullable = false)
	private Discussion discussion;

	public DiscussionVote(UserProfile userProfile, Discussion discussion, Byte voteValue) {
		super(userProfile, voteValue);
		this.discussion = discussion;
	}
}
