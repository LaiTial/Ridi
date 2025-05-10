package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.type.BuyType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookSaleOption extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private BookDetail bookDetail; // 책 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BuyType buyType;  // 대여 or 소장

    @Column(nullable = false)
    private Integer price; // 가격
}
