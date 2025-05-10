package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookDetailDTO;
import org.example.service.BookDetailService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookDetailController {

    private final BookDetailService bookDetailService;

    @PutMapping("/add/new-book")
    public void saveBookDetails(@RequestBody BookDetailDTO bookDetailDTO) {

    }
}
