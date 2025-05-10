package org.example.repository;

import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.Publisher;
import org.example.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // 상태가 ACTIVE인 책만 조회
    List<Book> findByStatus(Status status);

    // 지정한 작가의 책만 조회
    List<Book> findByAuthor(Author author);

    // 지정한 작가의 책 중 특정 상태인 책만 가져오기
    List<Book> findByAuthorAndStatus(Author author, Status status);

    // 지정한 출판사의 책만 조회
    List<Book> findByPublisher(Publisher publisher);

    // 지정한 출판사의 책 중 특정 상태인 책만 가져오기
    Page<Book> findByPublisherAndStatus(Publisher publisher, Status status, Pageable pageable);
}
