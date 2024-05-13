package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.SectionContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionContentRepository extends JpaRepository<SectionContent, Integer> {
    Optional<SectionContent> findFirstBySectionId(int sectionId);
}
