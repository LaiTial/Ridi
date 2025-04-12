package org.example.dto;

import lombok.*;
import org.example.entity.Book;
import org.example.type.PublicationStatus;
import org.example.type.SerialStatus;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookDTO {
    private Long id;                  // 고유 ID
    private String title;             // 책 제목
    private String category;          // 카테고리
    private String author;            // 작가
    private String publisher;         // 출판사
    private String imageUrl;          // 표지 (URL)
    private String isbn;              // ISBN
    private String description;       // 책 소개
    private Double rating;            // 별점
    private SerialStatus status;            // 연재 or 완결
    private PublicationStatus publicationStatus; // 출간중 or 중단
    private List<SectionKeywordListDTO> keywords;    // 키워드 목록

    public static BookDTO fromEntity(Book book) {
        if (book == null) {
            return null;
        }

        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .category(book.getCategory().getName()) // 카테고리 이름
                .author(book.getAuthor().getPenName())   // 작가 펜네임
                .publisher(book.getPublisher().getName()) // 출판사
                .imageUrl(book.getImageUrl())
                .isbn(book.getIsbn())
                .description(book.getDescription())
                .rating(book.getRating())
                .status(book.getStatus())
                .publicationStatus(book.getPublicationStatus())
                .keywords(book.getBookKeywords().stream()
                        .map(SectionKeywordListDTO::fromEntity) // SectionKeywordListDTO의 fromEntity 메서드를 사용
                        .collect(Collectors.toList()))
                .build();
    }
}
