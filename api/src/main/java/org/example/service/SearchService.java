package org.example.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.dto.BookDTO;
import org.example.dto.KeywordSearchDTO;
import org.example.entity.*;
import org.example.repository.BookKeywordRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final JPAQueryFactory queryFactory;
    private final BookKeywordRepository bookKeywordRepository;

    // 키워드로 책 검색
    public List<BookDTO> searchBooksByKeywordIds(KeywordSearchDTO keywordSearchDTO) {

        // 1. 페이징 처리
        Pageable pageable = PageRequest.of(keywordSearchDTO.getPage(), keywordSearchDTO.getSize(), keywordSearchDTO.getBookSortBy().getDirection(), keywordSearchDTO.getBookSortBy().getField()); // Pageable 객체 생성 (1페이지, 10개 항목)

        // 2. Q객체 가져오기
        QBook book = QBook.book;
        QBookKeyword bookKeyword = QBookKeyword.bookKeyword;

        // 3. QueryDSL로 검색
        List<Long> bookIds = queryFactory.select(book.id)
                .from(book)
                .join(book.bookKeywords, bookKeyword)
                .where(bookKeyword.keyword.id.in(keywordSearchDTO.getKeywordIds())) // 지정한 키워드 ID를 가진 책만 조회
                .groupBy(book.id) // 책 ID 기준으로 그룹화
                .having(bookKeyword.keyword.count().eq((long) keywordSearchDTO.getKeywordIds().size())) // 해당 책이 keywordIds에 해당하는 키워드를 모두 가지고 있는 경우만 필터링
                .offset(pageable.getOffset()) // 페이징 처리 : offset 설정
                .limit(pageable.getPageSize()) // 페이징 처리 : 한 페이지에 표시할 데이터의 개수 설정
                .fetch();

        List<Book> books = queryFactory
                .selectFrom(book)
                .distinct()
                .join(book.bookKeywords, bookKeyword).fetchJoin()
                .where(book.id.in(bookIds))
                .fetch();
        return books.stream()
                .map(BookDTO::fromEntity)
                .toList();
    } // offset 퍼포먼스 잘 안좋음
    // 페이징 처리 - page, 전체 page 조회 2번 조회
    // 순번을 알기 위해 전체 데이터 조회해야 한다는 문제
    // 스크롤은 마지막으로 조회한 애 ID만 있으면 된다.
    // 마지막으로 읽은애 + 10개
    // 스크롤과 페이징의 DB 퍼포먼스, 장단점.
}
