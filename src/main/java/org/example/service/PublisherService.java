package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.PublisherDTO;
import org.example.entity.Publisher;
import org.example.repository.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;

    // 새로운 작가를 저장하는 API
    @Transactional
    public void createPublisher(PublisherDTO publisherDTO) {
        Publisher publisher = Publisher.builder()
                .name(publisherDTO.getName())
                .books(new ArrayList<>()) // book 리스트 초기화
                .build();

        publisherRepository.save(publisher); // 새로운 작가 정보 저장
    }
}
