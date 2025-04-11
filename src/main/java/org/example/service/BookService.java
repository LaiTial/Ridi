package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateBookDTO;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.Category;
import org.example.entity.Publisher;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.CategoryRepository;
import org.example.repository.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    // 새로운 책 DB에 추가
    @Transactional
    public void createBook(CreateBookDTO createBookDTO) {

        // 1. 연관된 엔티티 조회
        Category category = categoryRepository.findByName(createBookDTO.getCategory()) // 카테고리 조회
                .orElseThrow(() -> new RidiException(ErrorCode.CATEGORY_NOT_FOUND));

        Author author = authorRepository.findByPenName(createBookDTO.getAuthor()) // 작가 조회
                .orElseThrow(() -> new RidiException(ErrorCode.AUTHOR_NOT_FOUND));

        Publisher publisher = publisherRepository.findByName(createBookDTO.getPublisher()) // 출판사 조회
                .orElseThrow(() -> new RidiException(ErrorCode.PUBLISHER_NOT_FOUND));

        // 2. 책 정보 저장
        Book book = Book.builder()
                .title(createBookDTO.getTitle())
                .category(category)
                .author(author)
                .publisher(publisher)
                .imageUrl(createBookDTO.getImageUrl())
                .isbn(createBookDTO.getIsbn())
                .description(createBookDTO.getDescription())
                .wishlistCount(0)        // 관심작 0 설정
                .rating(0.0)             // 별점 0 설정
                .status(createBookDTO.getStatus())
                .build();

        // 저장
        bookRepository.save(book);
    }
}
