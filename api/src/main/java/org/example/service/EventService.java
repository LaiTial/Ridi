package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.EventDTO;
import org.example.entity.Event;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    // 1. 할인률 이벤트 등록
    @Transactional
    public void createEvent(EventDTO dto) {

        // 1. 이벤트 객체 생성
        Event event = Event.builder()
                .eventName(dto.getEventName())
                .build();

        // 할인 정보 저장
        eventRepository.save(event);
    }

    // 2. 이벤트 정보 삭제
    @Transactional
    public void deleteEvent(Long eventID) {

        // 1. 할인 정보 조회
        Event event = eventRepository.findById(eventID)
                .orElseThrow(() -> new RidiException(ErrorCode.EVENT_NOT_FOUND));

        // 이벤트 정보 삭제
        eventRepository.delete(event);
    }


}
