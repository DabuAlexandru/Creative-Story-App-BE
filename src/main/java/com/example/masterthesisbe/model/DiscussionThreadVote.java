package com.example.masterthesisbe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "discussion_thread_votes", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_profile_id", "thread_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionThreadVote extends Vote {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "thread_id", nullable = false)
	private DiscussionThread thread;

	public DiscussionThreadVote(UserProfile userProfile, DiscussionThread thread, Byte voteValue) {
		super(userProfile, voteValue);
		this.thread = thread;
	}
}
