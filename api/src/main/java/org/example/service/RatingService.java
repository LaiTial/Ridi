package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.RatingDTO;
import org.example.entity.Book;
import org.example.entity.Rating;
import org.example.entity.RatingKey;
import org.example.entity.Users;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.BookRepository;
import org.example.repository.RatingRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final RatingRepository ratingRepository;

    // 새로운 별점이면 저장 or 존재 시 update
    @Transactional
    public void saveOrUpdateRating(RatingDTO ratingDTO) {

        // 1. 현재 로그인한 사용자 정보 조회
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. 사용자 객체 조회
        Users user = userRepository.findByLoginId(userId)
                .orElseThrow(() -> new RidiException(ErrorCode.USER_NOT_FOUND));

        // 3. 책 조회
        Book book = bookRepository.findById(ratingDTO.getBookID())
                .orElseThrow(() -> new RidiException(ErrorCode.BOOK_NOT_FOUND));

        // 4. 별점 등록 or 수정
        RatingKey id = new RatingKey(user.getId(), ratingDTO.getBookID()); // 복합키 생성
        Rating rating = Rating.builder() // 별점 객체 생성
                .id(id)
                .user(user)
                .book(book)
                .score(ratingDTO.getRating())
                .build();
        
        Rating find = ratingRepository.findById(id) // 이미 등록된 별점이 있는지 찾는다
                .orElse(rating);

        find.setScore(ratingDTO.getRating()); // 새 별점 등록
        ratingRepository.save(find);

        // 5. 책에 별점 정보 업데이트
        updateBookRating(book);
    }

    // 책에 대한 별점 삭제
    @Transactional
    public void deleteRating(Long bookId) {

        // 1. 현재 로그인한 사용자 정보 조회
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. 사용자 객체 조회
        Users user = userRepository.findByLoginId(userId)
                .orElseThrow(() -> new RidiException(ErrorCode.USER_NOT_FOUND));

        // 3. 등록된 별점 조회
        RatingKey id = new RatingKey(user.getId(), bookId);

        if (!ratingRepository.existsById(id)) {
            throw new RidiException(ErrorCode.RATING_NOT_FOUND);
        }

        ratingRepository.deleteById(id); // 삭제

        // 4. 책에 별점 정보 업데이트
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RidiException(ErrorCode.BOOK_NOT_FOUND));

        updateBookRating(book);
    }

    // 책에 별점 정보 업데이트
    @Transactional
    private void updateBookRating(Book book) {

        // 1. 지정한 책에 대한 별점들 조회
        List<Rating> ratings = ratingRepository.findByBook(book); // 별점 조회

        // 2. 별점 계산
        int totalScore = ratings.stream().mapToInt(Rating::getScore).sum();
        int count = ratings.size();

        // 3. 별점 등록
        if (count > 0) {
            book.setRating((double) totalScore / count); // 동시성 막는다
            book.setRatingCount(count);
        } else {
            book.setRating(0.0);
            book.setRatingCount(0);
        }
    }

    // 별점별로 통계 계산해서 반환
    @Transactional(readOnly = true)
    public Map<Integer, Long> getRatingStatisticsByBookId(Long bookId) {

        // 1. 책 ID로 해당 책 탐색
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RidiException(ErrorCode.BOOK_NOT_FOUND));

        // 2. 별점별로 통계 계산해서 반환
        return ratingRepository.findByBook(book).stream()
                .collect(Collectors.groupingBy(  // 실시간 x 배치 고려
                        Rating::getScore,
                        Collectors.counting()
                ));

    } // 스프링 배치
    // MSA 멀티 모듈 프로젝트

    // entity와 repository는 common
    // controller와 service는 api
    // batch와 common을 import해서 쓰도록
}
