package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.PublisherDTO;
import org.example.service.PublisherService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
