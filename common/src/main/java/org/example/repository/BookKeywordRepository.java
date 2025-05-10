package org.example.repository;

import org.example.entity.BookKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookKeywordRepository extends JpaRepository<BookKeyword, Long> {
}
