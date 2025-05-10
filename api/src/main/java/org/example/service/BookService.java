package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateBookDTO;
import org.example.entity.*;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.*;
import org.example.type.Status;
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
        Category category = categoryRepository.findById(createBookDTO.getCategory()) // 카테고리 조회
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
                .isbn(createBookDTO.getIsbn()) // unique 좋은 생각 x
                .description(createBookDTO.getDescription())
                .wishlistCount(0)        // 관심작 0 설정
                .rating(0.0)             // 별점 0 설정
                .ratingCount(0)
                .serialStatus(createBookDTO.getStatus()) // 연재 or 단행본
                .status(Status.ACTIVE) // 신규 책이니까 Active 설정
                .build();

        // 저장
        bookRepository.save(book);

        // 최상위 카테고리 찾기
        Category topCategory = category;
        while (topCategory.getParentCategory() != null) {
            topCategory = topCategory.getParentCategory();
        }

        // 3. 키워드 저장 및 연결
        Category finalTopCategory = topCategory;
        List<BookKeyword> bookKeywordList = createBookDTO.getKeywords().stream() // keyword 목록들 얻어오기
                .map(keywords -> {
                    Keyword keyword = keywordRepository.findByNameAndCategory(keywords, finalTopCategory)   // 키워드 객체 찾기
                            .orElseThrow(() -> new RidiException(ErrorCode.KEYWORD_NOT_FOUND));

                    // Book<->Keyword 중간 테이블에 저장
                    BookKeyword bookKeyword = BookKeyword.builder()
                            .book(book)
                            .keyword(keyword)
                            .build();

                    // 양방향 관계 설정
                    keyword.addBookKeyword(bookKeyword);
                    book.addBookKeyword(bookKeyword);

                    return bookKeyword;
                })
                .toList();


        bookKeywordRepository.saveAll(bookKeywordList);
    }

    // 책 판매 중단 처리를 하는 API
    @Transactional
    public void deactivateBook(Long id) {

        // 관리자 권한 체크
        // 그 일때만 허용

        // 1. 지정한 book 객체 찾기
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RidiException(ErrorCode.BOOK_NOT_FOUND));

        // 2. 값 변경
        book.setStatus(Status.INACTIVE); // 판매 중단 처리
    }
}
