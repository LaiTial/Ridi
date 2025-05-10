package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;
import org.example.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    // 새로운 작가 정보를 저장하는 API
    @PutMapping("/create/authors")
    public void createAuthors(
            @RequestBody AuthorDTO authorDTO
            ) {
        authorService.createAuthor(authorDTO); // 새로운 작가 정보 저장하는 API
    }

    // 작가를 삭제하는 API
    @DeleteMapping("/delete/authors")
    public void deleteAuthors(@RequestParam Long id) {
        authorService.deleteAuthor(id);
    }

    // 작가가 출간한 책 중에서 현재 판매 상태인 책만 조회하는 API
    @GetMapping("/author/book-list/active")
    public List<BookDTO> getActiveBooksByAuthor(@RequestParam Long id) {
        return authorService.findActiveBooksByAuthor(id);
    }


}
