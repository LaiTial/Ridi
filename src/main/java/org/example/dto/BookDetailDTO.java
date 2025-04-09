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
    private Integer volume; // 권수 (1권, 2권, 3권 등)
    private Integer price; // 가격
    private String text; // 책 내용
}
