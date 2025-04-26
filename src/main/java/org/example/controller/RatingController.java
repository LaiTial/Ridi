package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.RatingDTO;
import org.example.service.RatingService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    // 새로운 별점 정보를 저장하는 API
    @PostMapping("/books/user-ratings")
    public void registerRating(
            @RequestBody RatingDTO ratingDTO
            ) {
        ratingService.saveOrUpdateRating(ratingDTO); // 별점 업데이트
    }

    // 저장한 별점 정보를 삭제하는 API
    @DeleteMapping("/delete/ratings")
    public void deleteRating( @RequestParam Long bookID
    ) {
        ratingService.deleteRating(bookID); // 별점 삭제
    }

    // 책 ID를 받아 별점 통계를 얻는 API
    @GetMapping("/book-ratings/statistics")
    public Map<Integer, Long> getRatingStatistics(@RequestParam Long bookID) {
        return ratingService.getRatingStatisticsByBookId(bookID);
    }

}
