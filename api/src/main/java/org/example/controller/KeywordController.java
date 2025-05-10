package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookDTO;
import org.example.dto.CreateKeywordDTO;
import org.example.dto.KeywordSearchDTO;
import org.example.service.KeywordService;
import org.example.service.SearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;
    private final SearchService searchService;

    // 새로운 카테고리 저장 API
    @PutMapping("/api/keywords")
    public void createKeyword(
            @RequestBody CreateKeywordDTO createKeywordDTO // 요청으로 받은 카테고리 정보
    ) {
        keywordService.createKeyword(createKeywordDTO); // 키워드 생성 API
    }

    // 카테고리 ID로 검색하는 API
    @PostMapping("/search/keywords")
    public List<BookDTO> searchBooksByKeywordIds(@RequestBody KeywordSearchDTO keywordSearchDTO) {
        return searchService.searchBooksByKeywordIds(keywordSearchDTO);
    }
}
