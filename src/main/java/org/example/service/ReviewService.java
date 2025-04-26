package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ReviewDTO;
import org.example.entity.Book;
import org.example.entity.Review;
import org.example.entity.Users;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.BookRepository;
import org.example.repository.ReviewRepository;
import org.example.repository.UserRepository;
import org.example.type.ReviewSortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // 새로운 리뷰를 등록하는 API
    @Transactional
    public void createReview(ReviewDTO reviewDTO) {

        // 1. 현재 로그인한 사용자 정보 조회
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. 책과 사용자 탐색
        Book book = bookRepository.findById(reviewDTO.getBookID())
                .orElseThrow(() -> new RidiException(ErrorCode.BOOK_NOT_FOUND));

        Users user = userRepository.findByLoginId(userId)
                .orElseThrow(() -> new RidiException(ErrorCode.USER_NOT_FOUND));

        Review parentReview = reviewRepository.findById(reviewDTO.getParentReviewID())
                .orElseThrow(() -> new RidiException(ErrorCode.REVIEW_NOT_FOUND));

        // 3. 리뷰 객체 생성
        Review review = Review.builder()
                .books(book)
                .users(user)
                .likeCount(0)  // 기본값 0
                .isBuyer(false) // 아직 구매 테이블이 없으므로
                .content(reviewDTO.getContent()) // 리뷰 내용
                .reviewType(reviewDTO.getReviewType()) // 댓글 or 대댓글
                .parentReview(parentReview) // 대댓글이 아니면 null
                .build();

        // 리뷰 저장
        reviewRepository.save(review);

    }

    // 리뷰를 삭제하는 API
    @Transactional
    public void deleteReview(Long reviewId) {

        // 1. ID로 리뷰 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RidiException(ErrorCode.REVIEW_NOT_FOUND));

        // 2. 자식 리뷰가 있으면 먼저 삭제
        reviewRepository.deleteByParentReviewId(reviewId); // 대댓글 삭제

        // 3. 해당 리뷰 삭제
        reviewRepository.delete(review);
    }

    // 리뷰 내용 수정
    @Transactional
    public void updateReview(Long reviewId, String newContent) {

        // 1. ID로 리뷰 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RidiException(ErrorCode.REVIEW_NOT_FOUND));

        // 2. 리뷰 내용 수정
        review.setContent(newContent);
    }

    // 댓글 목록 반환(페이징 처리)
    public Page<Review> getComments(Long bookID, int page, int size, ReviewSortBy reviewSortBy) {

        // 최신순, 공감순, 별점 낮은순, 별점 높은순에 맞춰 페이징 처리
        Pageable pageable = PageRequest.of(page, size, Sort.by(reviewSortBy.getDirection(), reviewSortBy.getField()));

        return reviewRepository.findByBooks_Id(bookID, pageable);
    }

    // 대댓글 목록 반환(페이징 처리)
    public Page<Review> getReplies(Long parentReviewId, int page, int size) {

        // 대댓글은 무조건 오래된 것부터 최신순으로
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));

        return reviewRepository.findByParentReview_Id(parentReviewId, pageable);
    }
}
