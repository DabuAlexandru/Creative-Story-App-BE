package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.Discussion;
import com.example.masterthesisbe.model.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends
        PagingAndSortingRepository<Discussion, Integer>,
        JpaRepository<Discussion, Integer>
{
    Page<Discussion> findAllPaginateByAuthorId(Integer authorId, Pageable pageable);
}
