package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {
    List<Story> findByUserId(long userId);
}
