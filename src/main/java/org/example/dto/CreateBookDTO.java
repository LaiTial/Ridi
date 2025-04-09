package org.example.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateBookDTO {

    private String title;  // 책 이름
    private String contentCategory;  // 컨텐츠 카테고리 (웹툰, 만화, 웹소설, 도서)
    private String genre;  // 책 장르 (로맨스, 로판, 판타지, BL)
    private String author;  // 작가
    private String publisher;  // 출판사
    private String ImageUrl;  // 표지 (링크)
    private String isbn;  // ISBN
    private String description;  // 책 소개
    private Integer wishlistCount; // 관심작으로 등록한 사람 수
}
