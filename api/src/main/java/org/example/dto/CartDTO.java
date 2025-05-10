package org.example.dto;

import lombok.*;
import org.example.type.BuyType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartDTO {

    private Long bookDetailId; // 책 ID
    private BuyType buyType; // 구매 타입(대여 or 소장)
}
