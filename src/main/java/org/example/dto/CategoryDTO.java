package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoryDTO {
    private String name;           // 카테고리 이름 (예: 웹툰, 만화, 웹소설)
    private String parentName;         // 상위 카테고리 이름 (없으면 최상위 카테고리)
}
