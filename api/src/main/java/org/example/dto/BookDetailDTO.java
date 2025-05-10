package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookDetailDTO {
    private Long bookId; // 책 ID (Foreign Key)
    private String title; // 책 이름
    private Integer volume; // 권수 (1권, 2권, 3권 등)
    private Long letterCount; // 책 내용
}
