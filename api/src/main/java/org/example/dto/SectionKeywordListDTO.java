package org.example.dto;

import lombok.*;
import org.example.entity.BookKeyword;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SectionKeywordListDTO {
    private Long id; // 키워드 고유 ID
    private String name; // 키워드 명

    public static SectionKeywordListDTO fromEntity(BookKeyword bookKeyword) {
        return new SectionKeywordListDTO(
                bookKeyword.getKeyword().getId(),
                bookKeyword.getKeyword().getName()
        );
    }
}
