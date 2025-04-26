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
public class Cart extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 장바구니 id

    @ManyToOne
    private Users users;  // 사용자 ID

    @ManyToOne
    private BookDetail bookDetail; // 책 ID

    @Column(nullable = false)
    private BuyType buyType; // 대여 or 소장
} // 동기화 문제 일으킬 필요?
