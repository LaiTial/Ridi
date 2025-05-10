package org.example.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 처리하는 어드바이스
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(RidiException.class) // 컨트롤러에서 발생하는 익셉션을 핸들링하는 function을 만들어 원하는 방식으로 메시지

    // 특정해놓은 익셉션이 발생했을 때 Handling
    public ErrorResponse handleException(RidiException e, // 익셉션들이 어디에 발생하던 간에 딱 캐치해가지고 바로 처리해서 에러 리스펀스
                                         HttpServletRequest request) { // 요청이 들어왔던 HTTP 서블렛 Request를 함께 받을 수 있다.

        log.error("errorCode:{}, url:{}, message:{}", // error log에 좀 더 상세한 정보를 담을 수 있다
                e.getErrorCode(), request.getRequestURI(), e.getDetailMessage());

        return ErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    };

    // 공통적인, 일반적인 Exception이 발생했을 때 핸들링
    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class, // /create/developer POST로 정해졌는데 GET 요청을 한다던지 정해진 규격을 벗어난 요청
            MethodArgumentNotValidException.class // 자바 빈 validation에서 오류가 발생했을 때
    })
    public ErrorResponse handleBadRequest(Exception e,
                                           HttpServletRequest request) {
        log.error("url:{}, message:{}", request.getRequestURI(), e.getMessage());

        return ErrorResponse.builder()
                .errorCode(ErrorCode.INVALID_REQUEST)
                .errorMessage(ErrorCode.INVALID_REQUEST.getMessage())
                .build();

    }

    // 최후의 보루
    // 특정해놓은 익셉션도 아니고, 공통적으로 처리하는 일반 Exception이 아닌 알지 못하는 Exception이 발생했을 때 핸들링
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleEException(Exception e,
                                           HttpServletRequest request) {
        log.error("url:{}, message:{}", request.getRequestURI(), e.getMessage());

        return ErrorResponse.builder()
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .errorMessage(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
