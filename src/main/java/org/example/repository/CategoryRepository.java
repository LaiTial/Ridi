package org.example.repository;

import org.example.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // 이름으로 상위 카테고리 ID를 조회하는 함수
    Optional<Category> findByName(String name);
}
