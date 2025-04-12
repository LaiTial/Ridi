package org.example.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.dto.BookDTO;
import org.example.entity.*;
import org.example.repository.BookKeywordRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final JPAQueryFactory queryFactory;
    private final BookKeywordRepository bookKeywordRepository;

    // 키워드로 책 검색
    public List<BookDTO> searchBooksByKeywordIds(List<Long> keywordIds) {

        QBook book = QBook.book;
        QBookKeyword bookKeyword = QBookKeyword.bookKeyword;

        List<Book> books = queryFactory.selectFrom(book)

                .join(book.bookKeywords, bookKeyword)
                .where(bookKeyword.keyword.id.in(keywordIds)) // 지정한 책 ID를 가진 책만 조회
                .groupBy(book.id) // 책 ID 기준으로 그룹화
                .having(bookKeyword.keyword.count().eq((long) keywordIds.size())) // 해당 책이 keywordIds에 해당하는 키워드를 모두 가지고 있는 경우만 필터링
                .limit(10) // 페이징 처리 : 10건만
                .fetch();

        return books.stream()
                .map(BookDTO::fromEntity)
                .toList();
    }
}
