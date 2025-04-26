package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.type.Status;

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

    @Column(nullable = false)
    private Status status; // Active인지 Inactive인지
}
