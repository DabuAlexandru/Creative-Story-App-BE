package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParagraphRepository extends JpaRepository<Paragraph, Integer> {
}
