package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateBookDTO;
import org.example.entity.*;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.*;
import org.example.type.PublicationStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final KeywordRepository keywordRepository;
    private final BookKeywordRepository bookKeywordRepository;

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
                .status(createBookDTO.getStatus()) // 연재 or 단행본
                .publicationStatus(PublicationStatus.ACTIVE) // 신규 책이니까 Active 설정
                .build();

        // 저장
        bookRepository.save(book);

        // 3. 키워드 저장 및 연결
        List<BookKeyword> bookKeywordList = createBookDTO.getKeywords().stream() // keyword 목록들 얻어오기
                .map(keywords -> {
                    Keyword keyword = keywordRepository.findByName(keywords)
                            .orElseThrow(() -> new RidiException(ErrorCode.KEYWORD_NOT_FOUND));
                    return BookKeyword.builder()
                            .book(book)
                            .keyword(keyword)
                            .build();
                })
                .toList();

        bookKeywordRepository.saveAll(bookKeywordList );
    }
}
