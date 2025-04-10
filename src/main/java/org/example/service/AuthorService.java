package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.example.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    // 새로운 작가를 저장하는 API
    @Transactional
    public void createAuthor(AuthorDTO authorDTO) {
        Author author = Author.builder()
                .realName(authorDTO.getRealName())
                .penName(authorDTO.getPenName())
                .books(new ArrayList<>()) // 초기화
                .build();

        authorRepository.save(author);
    }
}
