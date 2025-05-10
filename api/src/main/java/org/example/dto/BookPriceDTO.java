package org.example.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookPriceDTO {

    private Long bookID; // 책 ID
    private List<BookSaleOptionDTO> price; // 대여:900, 소장:3000원 등 옵션별 가격
}
