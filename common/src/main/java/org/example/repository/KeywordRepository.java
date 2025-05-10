package org.example.repository;

import org.example.entity.Category;
import org.example.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    // 해당 카테고리 안에 키워드명으로 해당하는 키워드 객체 찾기
    Optional<Keyword> findByNameAndCategory(String name, Category category);

    // 지정한 카테고리의 키워드만 조회
    List<Keyword> findByCategory(Category category);
}
