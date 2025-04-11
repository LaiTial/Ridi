package org.example.repository;

import org.example.entity.Author;
import org.example.entity.Category;
import org.example.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    // 이름으로 출판사를 검색하는 함수
    Optional<Publisher> findByName(String name);
}
