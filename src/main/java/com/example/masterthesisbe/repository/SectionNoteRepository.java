package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.SectionNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionNoteRepository extends
        PagingAndSortingRepository<SectionNote, Integer>,
        JpaRepository<SectionNote, Integer> {
    Page<SectionNote> findAllBySectionId(long sectionId, Pageable pageable);
}
