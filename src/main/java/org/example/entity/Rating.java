package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Rating extends Base{

    @EmbeddedId
    private RatingKey id; // (사용자 ID, 책 ID)로 복합키

    @ManyToOne
    @MapsId("id")
    private Users user; // JPA 매핑 : 사용자 ID

    @ManyToOne
    @MapsId("bookId")
    private Book book; // JPA 매핑 : 책 ID

    @Column(nullable = false)
    private Integer score; // 별점 (ex. 4)
}
