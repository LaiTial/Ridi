package org.example.repository;

import org.example.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // 필명으로 작가 조회
    Optional<Author> findByPenName(String penName);
}
