package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateKeywordDTO;
import org.example.dto.SectionKeywordListDTO;
import org.example.entity.Category;
import org.example.entity.Keyword;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.CategoryRepository;
import org.example.repository.KeywordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final CategoryRepository categoryRepository;

    // 새로운 키워드를 DB에 추가하는 API
    @Transactional
    public void createKeyword(CreateKeywordDTO createKeywordDTO) {

        // 1. 키워드가 저장될 카테고리를 조회
        Category category = categoryRepository.findById(createKeywordDTO.getCategoryID()) // category ID 얻어오기
                .orElseThrow(() -> new RidiException(ErrorCode.CATEGORY_NOT_FOUND));

        // 2. 키워드 정보를 DB에 저장
        Keyword keyword = Keyword.builder()
                .name(createKeywordDTO.getName())
                .type(createKeywordDTO.getType())
                .category(category)
                .build();

        keywordRepository.save(keyword);
    }

    // 해당 카테고리의 키워드 목록 찾기
    @Transactional(readOnly = true)
    public Map<String, List<SectionKeywordListDTO>> getKeywordsByCategory(Long id) {

        // 1. 카테고리명으로 해당 카테고리 탐색
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RidiException(ErrorCode.CATEGORY_NOT_FOUND));

        // 2. 해당 카테고리에 연결된 키워드들을 타입별로 묶어서 반환
        return keywordRepository.findByCategory(category).stream() // category들의 keyword 리스트를 얻어서
                .collect(Collectors.groupingBy(
                        Keyword::getType,              // type별로 groupying
                        Collectors.mapping(
                                keyword -> new SectionKeywordListDTO(keyword.getId(), keyword.getName()),
                                Collectors.toList()
                        )
                ));

    }


}
