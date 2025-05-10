package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EventBookDTO {

    private Long eventID; // 이벤트ID
    private Long bookID; // 책 ID
    private Integer discountedPercent; // 할인 비율 (예: 10%)
}
