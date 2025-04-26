package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class BookDetail extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략
    private Long id; // 각 책별 ID

    @ManyToOne(cascade = CascadeType.ALL) // **LAZY : 연관된 엔티티는 실제 사용 시점에 조회
    Book book; // 책 ID (Foreign Key)

    @Column(nullable = false)
    private String title; // 책 이름 (예: 이세계 착각 헌터 2부 1권)

    @Column(nullable = false)
    private Integer volume; // 책 순서 (1, 2, 3)

    @Column(nullable = false)
    private Long letterCount; // 글자 수
}
