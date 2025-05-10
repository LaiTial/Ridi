package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private ErrorCode errorCode; // 예외 코드
    private String errorMessage;   // 예외 메시지
}
