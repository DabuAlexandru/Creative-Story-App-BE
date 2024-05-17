package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.Section;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends
        PagingAndSortingRepository<Section, Integer>,
        JpaRepository<Section, Integer>
{
    Page<Section> findAllByStoryId(long storyId, Pageable pageable);
    List<Section> findAllByStoryIdOrderByDisplayOrder(long storyId);
    Section findFirstByStoryIdOrderByLastModifiedDateDesc(long storyId);
    Integer countAllByStoryId(long storyId);
}
