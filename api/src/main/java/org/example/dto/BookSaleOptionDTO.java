package org.example.dto;

import lombok.*;
import org.example.entity.*;
import org.example.type.BuyType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookSaleOptionDTO {

    private BuyType type;  // 대여 or 소장
    private Integer price; // 가격

    public static BookSaleOption fromEntity(BookDetail book, BookSaleOptionDTO bookSaleOptionDTO) {

        return BookSaleOption.builder()
                .bookDetail(book)
                .buyType(bookSaleOptionDTO.getType())
                .price(bookSaleOptionDTO.getPrice())
                .build();
    }
}
