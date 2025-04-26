package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 키워드 ID

    @Column(nullable = false, unique = true)
    private String name; // 키워드 명

    @Column(nullable = false)
    private String type; // 키워드 타입 (장르/소재/직업 등)

    @ManyToOne
    private Category category; // 이 키워드가 속한 카테고리 (판타지/로판 등)

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookKeyword> bookKeywords; // 키워드를 사용하는 책 목록들

    // 책 목록에 키워드 추가
    public void addBookKeyword(BookKeyword bookKeyword) {

        if (this.bookKeywords == null) {
            this.bookKeywords = new ArrayList<>();
        }
        this.bookKeywords.add(bookKeyword); // 책 목록에 키워드 추가
        bookKeyword.setKeyword(this); // keyword 세팅
    }
}