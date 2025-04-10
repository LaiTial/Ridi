package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 책을 만들어 넣는 서비스
    /*@Transactional
    public void createBook() {



    }*/
}
