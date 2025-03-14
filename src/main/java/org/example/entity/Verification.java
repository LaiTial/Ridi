package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Verification extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략
    private Long id;

    @Column(nullable = false)
    private String verificationCode; // 인증 코드

    @Column(nullable = false)
    private String email;
}
