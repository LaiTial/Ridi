package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RatingDTO {
    private Long bookID; // 책 ID
    private Integer rating; // 별점
}
