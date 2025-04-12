package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateKeywordDTO;
import org.example.dto.SectionKeywordListDTO;
import org.example.entity.Category;
import org.example.entity.CategoryKeyword;
import org.example.entity.Keyword;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.CategoryKeywordRepository;
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
    private final CategoryKeywordRepository categoryKeywordRepository;

    // 새로운 키워드를 DB에 추가하는 API
    @Transactional
    public void createKeyword(CreateKeywordDTO createKeywordDTO) {

        // 1. 키워드 정보를 DB에 저장
        Keyword keyword = Keyword.builder()
                .name(createKeywordDTO.getName())
                .type(createKeywordDTO.getType())
                .build();

        keywordRepository.save(keyword);

        // 2. 키워드가 저장될 카테고리를 조회 및 저장
        List<CategoryKeyword> categoryKeywords = createKeywordDTO.getCategory().stream() // category 목록들 얻어오기
                .map(categoryName -> {
                    Category category = categoryRepository.findByName(categoryName)
                            .orElseThrow(() -> new RidiException(ErrorCode.CATEGORY_NOT_FOUND));

                    CategoryKeyword categoryKeyword = CategoryKeyword.builder()
                            .category(category)
                            .keyword(keyword)
                            .build();

                    // 양방향 연관관계 설정
                    category.getCategoryKeywords().add(categoryKeyword);
                    keyword.getCategoryKeywords().add(categoryKeyword);

                    return categoryKeyword;
                })
                .toList();

        categoryKeywordRepository.saveAll(categoryKeywords);

    }

    // 해당 카테고리의 키워드 목록 찾기
    @Transactional(readOnly = true)
    public Map<String, List<SectionKeywordListDTO>> getKeywordsByCategoryName(String name) {

        // 1. 카테고리명으로 해당 카테고리 탐색
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new RidiException(ErrorCode.CATEGORY_NOT_FOUND));

        return category.getCategoryKeywords().stream() // category들의 keyword 리스트를 얻어서
                .map(CategoryKeyword::getKeyword)
                .collect(Collectors.groupingBy(
                        Keyword::getType,
                        Collectors.mapping(
                                keyword -> new SectionKeywordListDTO(keyword.getId(), keyword.getName()),
                                Collectors.toList()
                        )
                ));

    }


}
