package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateBookDTO;
import org.example.service.BookService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
