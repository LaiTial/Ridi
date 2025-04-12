package org.example.repository;

import org.example.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    // 키워드명으로 해당하는 키워드 객체 찾기
    Optional<Keyword> findByName(String name);
}
