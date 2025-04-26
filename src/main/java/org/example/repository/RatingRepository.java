package org.example.repository;

import org.example.entity.Book;
import org.example.entity.Rating;
import org.example.entity.RatingKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, RatingKey> {

    // 지정한 책에 대한 모든 별점 리스트를 반환
    List<Rating> findByBook(Book book);
}
