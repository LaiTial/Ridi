package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Category extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 카테고리 ID

    @Column(nullable = false, unique = true)
    private String name; // 카테고리 이름 (예: 웹툰, 만화, 웹소설 등)

    // 상위 카테고리 (자기 자신 참조)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;  // 부모 카테고리 (상위 카테고리)

    @JsonIgnore
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> subCategories; // 하위 카테고리 리스트

    @OneToMany
    private List<CategoryKeyword> categoryKeywords; // 카테고리 별 키워드들
    // 카테고리 정확히 depth 표현

    // Category의 키워드 리스트에 값 넣어주기
    public void addCategoryKeyword(CategoryKeyword categoryKeyword) {
        this.categoryKeywords.add(categoryKeyword);
        categoryKeyword.setCategory(this);
    }
}
