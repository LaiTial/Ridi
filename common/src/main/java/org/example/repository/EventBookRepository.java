package org.example.repository;

import org.example.entity.Event;
import org.example.entity.EventBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventBookRepository extends JpaRepository<EventBook, Long> {

    List<EventBook> findByEvent(Event event); // 특정 이벤트에 속한 EventBook 리스트 조회
}
