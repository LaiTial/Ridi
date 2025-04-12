package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Author extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 작가 ID

    @Column(nullable = false, unique = true)
    private String realName; // 작가 이름

    @Column(nullable = false)
    private String penName;  // 작가의 필명

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books; // 작가 별로 책 조회

    // 책 목록에 추가
    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthor(this); // 주인 쪽(BookKeyword)도 설정!
    }
}
