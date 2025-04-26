package org.example.repository;

import org.example.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 해당 리뷰의 대댓글 검색
    void deleteByParentReviewId(Long parentReviewId);

    // 지정한 책의 댓글 목록(페이징 추가)
    Page<Review> findByBooks_Id(Long bookId, Pageable pageable);

    // 해당 리뷰의 대댓글 목록(+페이징 추가)
    Page<Review> findByParentReview_Id(Long parentReviewId, Pageable pageable);
}
