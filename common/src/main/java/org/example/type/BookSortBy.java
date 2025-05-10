package org.example.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@Getter
public enum BookSortBy {
    LATEST("createdAt", Sort.Direction.DESC),  // 최신순
    POPULARITY_ASC("wishlistCount", Sort.Direction.ASC),  // 인기순 (wishlistCount)
    RATING("rating", Sort.Direction.ASC),  // 별점순
    REVIEW_COUNT_DESC("reviewCount", Sort.Direction.DESC),  // 리뷰 많은 순
    PRICE_ASC("price", Sort.Direction.ASC);  // 가격순

    private final String field;
    private final Sort.Direction direction;

}
