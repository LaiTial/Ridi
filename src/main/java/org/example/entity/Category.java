package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;  // 부모 카테고리 (상위 카테고리)

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> subCategories; // 하위 카테고리 리스트
    
    // 카테고리 정확히 depth 표현
}
