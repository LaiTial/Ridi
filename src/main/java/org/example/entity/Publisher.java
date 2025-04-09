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
public class Publisher extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 출판사 ID

    @Column(nullable = false, unique = true)
    private String name; // 출판사 이름

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true) // 기본이 LAZY
    private List<Book> books; // 출판사 별로 책 조회
}
