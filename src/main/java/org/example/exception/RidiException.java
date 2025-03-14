package org.example.exception;

import lombok.Getter;

@Getter
public class RidiException extends RuntimeException{

    private final ErrorCode errorCode; // 에러코드
    private final String detailMessage; // 자세한 메시지

    public RidiException(ErrorCode errorCode) { // detail 메시지 없이 단순히 코드만 받으면 일반적인 케이스란 의미
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detailMessage = errorCode.getMessage(); // 기본 메시지를 담아준다.
    }


    public RidiException(ErrorCode loginErrorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = loginErrorCode;
        this.detailMessage = detailMessage; // 상세 메시지를 담아줌
    }
}
