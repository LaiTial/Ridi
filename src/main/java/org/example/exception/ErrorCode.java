package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 에러코드를 정의해주고 에러 코드의 기본이 되는 메시지를 property로 담아준다.
@AllArgsConstructor
@Getter
public enum LoginErrorCode {

    DUPLICATE_LOGIN_ID("로그인 ID 중복 오류"),
    DUPLICATE_EMAIL("이메일 중복 오류");
    private final String message;
}
