package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookPriceDTO;
import org.example.dto.BookSaleOptionDTO;
import org.example.entity.BookDetail;
import org.example.entity.BookSaleOption;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.BookDetailRepository;
import org.example.repository.BookSalesOptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookPriceService {

    private final BookSalesOptionRepository bookSalesOptionRepository;
    private final BookDetailRepository bookDetailRepository;

    public void saveBookPrices(BookPriceDTO dto) {

        // 1. 단권 책 개체 찾기
        BookDetail book = bookDetailRepository.findById(dto.getBookID())
                .orElseThrow(() -> new RidiException(ErrorCode.BOOK_NOT_FOUND));

        // 2. 옵션별 가격 저장
        List<BookSaleOption> bookSaleOption = dto.getPrice().stream()
                .map(bookSaleOptionDTO ->
                        BookSaleOptionDTO.fromEntity(book, bookSaleOptionDTO)
                )
                .toList();

        bookSalesOptionRepository.saveAll(bookSaleOption);

    }
}
