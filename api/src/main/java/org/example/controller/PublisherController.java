package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookDTO;
import org.example.dto.PublisherActiveBookDTO;
import org.example.dto.PublisherDTO;
import org.example.service.PublisherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    // 새로운 출판사 정보를 저장하는 API
    @PutMapping("/create/publisher")
    public void createPublisher(
            @RequestBody PublisherDTO publisherDTO
            ) {
        publisherService.createPublisher(publisherDTO); // 새로운 출판사 정보 저장하는 API
    }

    // 출판사를 삭제하는 API
    @DeleteMapping("/delete/publishers")
    public void deleteAuthors(@RequestParam Long id) {
        publisherService.deletePublisher(id);
    }

    // 출판사가 출간한 책 중에서 현재 판매 상태인 책만 조회하는 API
    @GetMapping("/publisher/book-list/active")
    public List<BookDTO> getActiveBooksByPublisher(@RequestBody PublisherActiveBookDTO publisherActiveBookDTO) {
        return publisherService.findActiveBooksByPublisher(publisherActiveBookDTO);
    }
}
