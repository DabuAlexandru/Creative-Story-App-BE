package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.dto.section.SectionWithContentResponseDto;
import com.example.masterthesisbe.model.Section;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SectionRepository extends
        PagingAndSortingRepository<Section, Integer>,
        JpaRepository<Section, Integer>
{
    Page<Section> findAllByStoryId(long storyId, Pageable pageable);
    List<Section> findAllByStoryIdOrderByDisplayOrder(long storyId);
    @Query(value = "SELECT s.*, sc.content"
            + " from section s left join section_content sc on s.id=sc.section_id"
            + " where s.story_id = ?1"
            + " order by s.display_order"
            , nativeQuery = true)
    List<Map<String, Object>> findAllWithContentByStoryIdOrderByDisplayOrder(long storyId);
    Section findFirstByStoryIdOrderByLastModifiedDateDesc(long storyId);
    Integer countAllByStoryId(long storyId);
}
