package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;
import org.example.entity.Author;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.type.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    // 새로운 작가를 저장하는 API
    @Transactional
    public void createAuthor(AuthorDTO authorDTO) {
        Author author = Author.builder()
                .realName(authorDTO.getRealName())
                .penName(authorDTO.getPenName())
                .status(Status.ACTIVE) // Active : 활동
                //.books(new ArrayList<>()) // 초기화
                .build();

        authorRepository.save(author);
    }

    // 작가를 삭제하는 API
    @Transactional
    public void deleteAuthor(Long id) {

        // 1. 작가 객체 찾기
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RidiException(ErrorCode.AUTHOR_NOT_FOUND));

        author.setStatus(Status.INACTIVE);

        // 2. 작가가 출간한 책 모두 판매 중단 처리
        bookRepository.findByAuthor(author).forEach(
                book -> book.setStatus(Status.INACTIVE) // 모두 판매 중단 처리
        );
    }

    // 작가가 출간한 책 중에서 현재 판매 상태인 책만 조회
    public List<BookDTO> findActiveBooksByAuthor(Long id) {
        // 1. 작가 객체 찾기
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RidiException(ErrorCode.AUTHOR_NOT_FOUND));


        // 2. 작가가 출간한 책 중에서 현재 판매 상태인 책 조회
        return bookRepository.findByAuthorAndStatus(author, Status.ACTIVE).stream()
                .map(BookDTO::fromEntity) // 책 정보 형식으로 변환
                .toList();
    }
}
