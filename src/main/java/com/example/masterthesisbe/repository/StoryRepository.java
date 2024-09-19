package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends
        JpaRepository<Story, Integer>,
        JpaSpecificationExecutor<Story>,
        PagingAndSortingRepository<Story, Integer> {
    List<Story> findByAuthorIdAndPublished(long authorId, boolean isPublished);
}
