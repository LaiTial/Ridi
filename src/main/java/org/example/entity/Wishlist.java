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
public class Wishlist extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 장바구니 id

    @ManyToOne
    private Users users;  // 사용자 ID

    @ManyToOne
    private BookDetail bookDetail; // 책 ID

    @Column(nullable = false)
    private Integer price; // 상품 가격

    @Column(nullable = false)
    private BuyType buyType; // 대여 or 소장
}
