package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookDetailDTO;
import org.example.entity.Book;
import org.example.entity.BookDetail;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.BookDetailRepository;
import org.example.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookDetailService {

    private final BookRepository bookRepository;
    private final BookDetailRepository bookDetailRepository;

    // 새로운 책(단권) 출간하는 API
    @Transactional
    public void createBookDetail(BookDetailDTO dto) {

        // 1. 책에 대한 정보 조회
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RidiException(ErrorCode.BOOK_NOT_FOUND));

        // 2. 단권 객체 생성
        BookDetail bookDetail = BookDetail.builder()
                .book(book)
                .title(dto.getTitle())
                .volume(dto.getVolume())
                .letterCount(dto.getLetterCount())
                .build();

        bookDetailRepository.save(bookDetail); // DB에 값 저장
    }
}
