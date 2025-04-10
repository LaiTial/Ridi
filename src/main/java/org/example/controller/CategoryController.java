package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryDTO;
import org.example.service.CategoryService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 새로운 카테고리 저장 API
    @PutMapping("/create/category")
    public void createCategory(
            @RequestBody CategoryDTO categoryDTO // 요청으로 받은 카테고리 정보
    ) {
        categoryService.createCategory(categoryDTO); // 카테고리 생성 API
    }
}
