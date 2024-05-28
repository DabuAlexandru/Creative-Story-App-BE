package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.DiscussionThread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionThreadRepository extends
        PagingAndSortingRepository<DiscussionThread, Integer>,
        JpaRepository<DiscussionThread, Integer>
{
    Page<DiscussionThread> findAllPaginateByMainThreadIdOrderByCreatedByDesc(Integer mainDiscussionThreadId, Pageable pageable);
    Page<DiscussionThread> findAllPaginateByDiscussionIdOrderByCreatedByDesc(Integer discussionId, Pageable pageable);
    List<DiscussionThread> findAllByMainThreadIdOrderByCreatedByDesc(Integer mainDiscussionThreadId);
    Page<DiscussionThread> findAllPaginateByAuthorId(Integer authorId, Pageable pageable);
}
