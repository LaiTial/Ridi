package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookDTO;
import org.example.dto.PublisherActiveBookDTO;
import org.example.dto.PublisherDTO;
import org.example.entity.Publisher;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.BookRepository;
import org.example.repository.PublisherRepository;
import org.example.type.BookSortBy;
import org.example.type.Status;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    // 새로운 작가를 저장하는 API
    @Transactional
    public void createPublisher(PublisherDTO publisherDTO) {
        Publisher publisher = Publisher.builder()
                .name(publisherDTO.getName())
                .status(Status.ACTIVE) // Active : 활동
                //.books(new ArrayList<>()) // 책 목록 초기화
                .build();

        publisherRepository.save(publisher); // 새로운 작가 정보 저장
    }

    // 출판사를 삭제하는 API
    @Transactional
    public void deletePublisher(Long id) {

        // 1. 출판사 객체 찾기
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RidiException(ErrorCode.PUBLISHER_NOT_FOUND));

        publisher.setStatus(Status.INACTIVE);

        // 2. 출판사가 출간한 책 모두 판매 중단 처리
        bookRepository.findByPublisher(publisher).forEach(
                book -> book.setStatus(Status.INACTIVE) // 모두 판매 중단 처리
        );
    }

    // 출판사가 출간한 책 중에서 현재 판매 상태인 책만 조회
    public List<BookDTO> findActiveBooksByPublisher(PublisherActiveBookDTO publisherActiveBookDTO) {

        // 1. 출판사 객체 찾기
        Publisher publisher = publisherRepository.findById(publisherActiveBookDTO.getId())
                .orElseThrow(() -> new RidiException(ErrorCode.PUBLISHER_NOT_FOUND));


        // 2. 출판사가 출간한 책 중에서 현재 판매 상태인 책 조회
        // 최신순, 리뷰 많은순, 가격순 중 택 1
        Pageable pageable = PageRequest.of(publisherActiveBookDTO.getPage(), publisherActiveBookDTO.getSize(), publisherActiveBookDTO.getBookSortBy().getDirection(), publisherActiveBookDTO.getBookSortBy().getField());

        return bookRepository.findByPublisherAndStatus(publisher, Status.ACTIVE, pageable).stream()
                .map(BookDTO::fromEntity) // 책 정보 형식으로 변환
                .toList();
    }
}
