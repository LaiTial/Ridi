package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.type.ReviewType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Review extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 리뷰 ID

    @ManyToOne
    @JoinColumn(name = "book_id") // ✅ 다대일 관계 (FK)
    private Book books; // 리뷰가 달린 책의 ID

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users; // 리뷰를 쓴 사용자 ID

    @Column(nullable = false)
    private Integer likeCount; // 공감수(기본값 0)

    @Column(nullable = false)
    private Boolean isBuyer; // true : 구매자, false : 예)미리보기만 본 사용자

    @Column(nullable = false)
    private String content;  // 리뷰 내용

    @Column(nullable = false)
    private ReviewType reviewType;  // 댓글 or 대댓글

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Review parentReview;  // 부모 리뷰 ID (대댓글일 경우만 값 존재)
}
