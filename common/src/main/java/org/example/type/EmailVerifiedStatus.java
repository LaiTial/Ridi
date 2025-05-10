package org.example.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailVerifiedStatus {
    VERIFIED("인증된 사용자"), // 이메일 인증 완료
    UNVERIFIED("인증되지 않은 사용자"); // 이메일 인증 미완료

    private final String description;
}
