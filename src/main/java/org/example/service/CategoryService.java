package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryDTO;
import org.example.entity.Category;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void createCategory(CategoryDTO categoryDTO) {

        Category parent = null;

        // 상위 카테고리가 지정된 경우, 해당 카테고리를 조회
        if (categoryDTO.getParentName() != null) {
            parent = categoryRepository.findByName(categoryDTO.getParentName())
                    .orElseThrow(() -> new IllegalArgumentException("해당 이름의 부모 카테고리가 존재하지 않습니다."));
        }

        // 새 카테고리 생성
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .parentCategory(parent)
                .build();

        // DB에 저장
        categoryRepository.save(category);
    }
}
