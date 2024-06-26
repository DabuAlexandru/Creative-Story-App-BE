package com.example.masterthesisbe.specification;

import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.model.UserProfile;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class UserProfileSpecification {
    public static Specification<UserProfile> isAuthor() {
        return (root, query, builder) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            var storyRoot = subquery.from(Story.class);
            subquery.select(storyRoot.get("author").get("id"));
            subquery.where(builder.equal(storyRoot.get("author").get("id"), root.get("id")));

            return builder.exists(subquery);
        };
    }

    public static Specification<UserProfile> penNameContains(String penName) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("penName")), "%" + penName.toLowerCase() + "%");
    }
}
