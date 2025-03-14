package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.type.EmailVerifiedStatus;
import org.example.type.Gender;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Users extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략
    private Long id;

    @Column(nullable = false, unique = true) // ID는 null이 아니고 유니크해야 함
    private String loginId; // 사용자 ID (이메일 등)

    @Column(nullable = false)
    private String password; // 비밀번호 // 인코딩해서 넣기.

    @Column(nullable = false, unique = true) // 이메일은 유니크해야 함
    private String email; // 이메일 주소

    @Column(nullable = false)
    private String name; // 사용자 이름

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    private Gender gender; // 성별

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    private EmailVerifiedStatus emailVerified; // 이메일 인증 여부

    private Integer birthYear; // 생년월일
}
