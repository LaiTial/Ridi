package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class EventBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Event event; // 이벤트 ID

    @ManyToOne(fetch = FetchType.LAZY)
    private BookDetail bookDetail; // 이벤트 대상 책

    @Column(nullable = false, unique = true)
    private Integer discountedPercent; // 할인 비율 (예: 10%)
}
