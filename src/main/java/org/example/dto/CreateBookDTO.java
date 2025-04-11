package org.example.dto;

import lombok.*;
import org.example.type.SerialStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateBookDTO {

    private String title;             // 책 제목
    private String category;          // 카테고리 이름
    private String author;            // 작가
    private String publisher;         // 출판사
    private String imageUrl;          // 표지 (URL)
    private String isbn;              // ISBN
    private String description;       // 책 소개
    private SerialStatus status;      // 연재 상태 (SERIAL / COMPLETED)
}
