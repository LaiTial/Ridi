package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateBookDTO;
import org.example.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 새로운 책 정보를 저장하는 API
    @PutMapping("/create/books")
    public void createBooks(
            @RequestBody CreateBookDTO createBookDTO
            ) {
        bookService.createBook(createBookDTO); // 새로운 책 정보를 저장하는 API
    }

    // 책 판매 중지 처리를 하는 API
    @DeleteMapping("/books/deactivate")
    public void deactivateBook(
            @RequestParam Long id
    ) {
        bookService.deactivateBook(id); // 지정한 책 판매 중단 처리
    }
}
