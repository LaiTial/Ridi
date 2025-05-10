package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.type.SerialStatus;
import org.example.type.Status;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Book extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략
    private Long id; // 책 ID

    @Column(nullable = false)
    private String title;  // 책 이름

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category; // 책의 카테고리 목록

    @ManyToOne
    @JoinColumn(name = "author")
    private Author author;  // 작가

    @ManyToOne
    @JoinColumn(name = "publisher")
    private Publisher publisher;  // 출판사

    @Column(nullable = false)
    private String imageUrl;  // 대표 표지 (링크)

    @Column(nullable = false)
    private String isbn;  // ISBN

    @Column(nullable = false)
    private String description;  // 책 소개

    @Column(nullable = false)
    private Integer wishlistCount; // 관심작 등록 수 (기본값 0)

    @Column(nullable = false)
    private Double rating; // 별점 (기본값 0)

    @Column(nullable = false)
    private Integer ratingCount; // 별점을 등록한 사람 수

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SerialStatus serialStatus; // 연재 or 완결

    @Column(nullable = false)
    private Status status; // 출간중 or 중단

    // ✅ mappedBy에 들어가는 건 "필드명"!
    @OneToMany(mappedBy = "books", cascade = CascadeType.ALL, orphanRemoval = true) // ✅ 일대다 관계
    private List<Review> reviews;  // 책에 대한 리뷰 목록

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookKeyword> bookKeywords; // 책에 대한 키워드 목록


    // 리뷰 목록 추가
    public void addReview(Review review) {
        this.reviews.add(review); // 책의 리뷰 목록에 추가
        review.setBooks(this); // 주인 쪽(BookKeyword)도 설정!
    }

    // 키워드 목록 추가
    public void addBookKeyword(BookKeyword bookKeyword) {

        if (this.bookKeywords == null) {
            this.bookKeywords = new ArrayList<>();
        }

        this.bookKeywords.add(bookKeyword); // 책의 키워드 리스트에 추가
        bookKeyword.setBook(this); // 주인 쪽(BookKeyword)도 설정!
    }
}
// QueryDSL을 이용해 검색 기능 구현
// 페이징 처리

