package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ReviewDTO;
import org.example.entity.Review;
import org.example.service.ReviewService;
import org.example.type.ReviewSortBy;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 1. 새로운 리뷰 등록
    @PutMapping("/books/user-reviews/registers")
    public void createReview(@RequestBody ReviewDTO reviewDTO) {
        reviewService.createReview(reviewDTO);
    }

    // 2. 리뷰 삭제
    @DeleteMapping("/delete/reviews")
    public void deleteReview(@RequestParam Long reviewId) {
        reviewService.deleteReview(reviewId);
    }

    // 3. 리뷰 수정
    @PostMapping("/reviews/update")
    public void updateReview(@RequestParam Long reviewId, @RequestBody String newContent) {
        reviewService.updateReview(reviewId, newContent);
    }

    // 4. 댓글 목록 조회 (페이징, 정렬)
    @GetMapping("/books/reviews/list")
    public Page<Review> getComments(@RequestParam Long bookId,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "LATEST") ReviewSortBy sortBy) {
        return reviewService.getComments(bookId, page, size, sortBy);
    }

    // 5. 대댓글 목록 조회 (페이징)
    @GetMapping("/books/reviews/replies/list")
    public Page<Review> getReplies(@RequestParam Long parentReviewId,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return reviewService.getReplies(parentReviewId, page, size);
    }
}
