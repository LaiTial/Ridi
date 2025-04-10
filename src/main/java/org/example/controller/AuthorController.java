package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthorDTO;
import org.example.service.AuthorService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
