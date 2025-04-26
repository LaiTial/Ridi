package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryDTO;
import org.example.entity.Category;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 새로운 카테고리를 생성하는 API
    @Transactional
    public void createCategory(CategoryDTO categoryDTO) {

        Category parent = null;

        // 상위 카테고리가 지정된 경우, 해당 카테고리를 조회
        if (categoryDTO.getParentID() != null) {
            parent = categoryRepository.findById(categoryDTO.getParentID())
                    .orElseThrow(() -> new RidiException(ErrorCode.CATEGORY_NOT_FOUND));
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
