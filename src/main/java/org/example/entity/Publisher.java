package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Publisher extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 출판사 ID

    @Column(nullable = false, unique = true)
    private String name; // 출판사 이름

    @JsonIgnore // 무한 참조 막기
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true) // 기본이 LAZY
    private List<Book> books; // 출판사 별로 책 조회

    // 책 목록에 추가
    public void addBook(Book book) {
        this.books.add(book);
        book.setPublisher(this); // 주인 쪽(BookKeyword)도 설정!
    }
}
