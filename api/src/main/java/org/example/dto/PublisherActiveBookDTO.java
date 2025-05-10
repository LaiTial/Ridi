package org.example.dto;

import lombok.*;
import org.example.type.BookSortBy;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PublisherActiveBookDTO {

    private Long id; // 출판사 ID
    private BookSortBy bookSortBy; // 책 정렬 순서(최신순, 가격 낮은순, 가격 높은순)
    private int page; // 페이지 번호
    private int size; // 페이지 크기
}
