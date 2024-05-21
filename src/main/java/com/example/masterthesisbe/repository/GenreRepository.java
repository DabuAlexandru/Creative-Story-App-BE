package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.Genre;
import com.example.masterthesisbe.model.SectionNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    List<Genre> findAllByMainGenreIdOrderByName(Integer mainGenreId);
}
