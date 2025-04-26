package org.example.dto;

import lombok.*;
import org.example.type.BookSortBy;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class KeywordSearchDTO {
    private int page; // 페이지 번호
    private int size; // 페이지 크기
    private BookSortBy bookSortBy; // 정렬 순서
    private List<Long> keywordIds; // 검색할 키워드들
}
