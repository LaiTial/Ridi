package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryDTO;
import org.example.dto.SectionKeywordListDTO;
import org.example.service.CategoryService;
import org.example.service.KeywordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final KeywordService keywordService;

    // 새로운 카테고리 저장 API
    @PutMapping("/create/category")
    public void createCategory(
            @RequestBody CategoryDTO categoryDTO // 요청으로 받은 카테고리 정보
    ) {
        categoryService.createCategory(categoryDTO); // 카테고리 생성 API
    }

    // 카테고리 별 키워드 목록 반환 API
    @GetMapping("/category/name")
    public Map<String, List<SectionKeywordListDTO>> getKeywordsByCategoryName(@RequestParam String category) {
        return keywordService.getKeywordsByCategoryName(category);
    }

}
