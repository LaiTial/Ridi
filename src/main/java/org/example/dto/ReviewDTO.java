package org.example.dto;

import lombok.*;
import org.example.entity.Book;
import org.example.type.ReviewType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewDTO {

    private Long bookID; // 리뷰가 달린 책의 ID
    private String content;  // 리뷰 내용
    private ReviewType reviewType;  // 댓글 or 대댓글
    private Long parentReviewID;  // 부모 리뷰 ID (대댓글일 경우만 값 존재)
}