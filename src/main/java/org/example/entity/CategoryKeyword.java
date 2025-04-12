package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CategoryKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 키워드가 속한 카테고리 (최상위만 사용)
    @ManyToOne
    private Category category;

    // 연결된 키워드
    @ManyToOne
    private Keyword keyword;
}
