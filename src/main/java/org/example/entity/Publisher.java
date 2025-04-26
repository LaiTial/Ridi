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
public class Publisher extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 출판사 ID

    @Column(nullable = false, unique = true)
    private String name; // 출판사 이름

    @Column(nullable = false)
    private Status status; // 활동중 or 비활동
}
