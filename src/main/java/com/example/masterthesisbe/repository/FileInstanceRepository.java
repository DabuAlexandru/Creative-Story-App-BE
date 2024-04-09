package com.example.masterthesisbe.repository;

import com.example.masterthesisbe.model.FileInstance;
import com.example.masterthesisbe.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInstanceRepository extends JpaRepository<FileInstance, Integer> {
    FileInstance findByFileName(String filename);
    @Transactional
    Long deleteByFileName(String filename);
    boolean existsByFileNameAndUser(String filename, User user);
}
