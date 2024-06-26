package com.example.masterthesisbe.specification;

import com.example.masterthesisbe.model.Story;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class StorySpecification {

    public static Specification<Story> titleContains(String title) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Story> authorIdIn(List<Integer> authorIds) {
        return (root, query, builder) -> root.get("author").get("id").in(authorIds);
    }

    public static Specification<Story> genresIn(List<Integer> genreIds) {
        return (root, query, builder) -> root.join("genres").get("id").in(genreIds);
    }
}
