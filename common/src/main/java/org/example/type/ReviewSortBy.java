package org.example.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum ReviewSortBy {
    LATEST("createdAt", Sort.Direction.DESC), // 최신순
    LIKE_COUNT("likeCount", Sort.Direction.DESC), // 공감수 많은순
    RATING_ASC("rating", Sort.Direction.ASC), // 별점 낮은순
    RATING_DESC("rating", Sort.Direction.DESC); // 별점 많은순

    private final String field;
    private final Sort.Direction direction;
}
