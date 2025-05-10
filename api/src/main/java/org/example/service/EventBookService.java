package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.EventBookDTO;
import org.example.entity.BookDetail;
import org.example.entity.Event;
import org.example.entity.EventBook;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.BookDetailRepository;
import org.example.repository.EventBookRepository;
import org.example.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventBookService {

    private final EventBookRepository eventBookRepository;
    private final EventRepository eventRepository;
    private final BookDetailRepository bookDetailRepository;

    // 1. 이벤트 책 할인 등록
    @Transactional
    public void addBookToEvent(EventBookDTO dto) {

        // 1. 해당 이벤트가 존재하는지 확인
        Event event = eventRepository.findById(dto.getEventID())
                .orElseThrow(() -> new RidiException(ErrorCode.EVENT_NOT_FOUND));


        // 2. 해당 책 객체가 존재하는지 확인
        BookDetail bookDetail = bookDetailRepository.findById(dto.getBookID())
                .orElseThrow(() -> new RidiException(ErrorCode.BOOK_NOT_FOUND));

        // 3. 할인률 객체 생성
        EventBook eventBook = EventBook.builder()
                .bookDetail(bookDetail)
                .event(event)
                .discountedPercent(dto.getDiscountedPercent())
                .build();

        // 할인 정보 저장
        eventRepository.save(event);
    }

    // 2. 이벤트 특정 책 할인 정보 수정
    @Transactional
    public void updateDiscount(Long eventBookID, Integer discountedPercent) {

        // 1. 할인 정보 조회
        EventBook event = eventBookRepository.findById(eventBookID) // 책이 하나의 이벤트에만 포함되있을까?
                .orElseThrow(() -> new RidiException(ErrorCode.DISCOUNT_NOT_FOUND));

        // 2. 할인 비율 수정
        event.setDiscountedPercent(discountedPercent);

        // 수정된 할인 정보 저장
        eventBookRepository.save(event);
    }

    // 3. 특정 이벤트의 책들 리스트 반환

}
