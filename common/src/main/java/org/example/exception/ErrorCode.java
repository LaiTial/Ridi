package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 에러코드를 정의해주고 에러 코드의 기본이 되는 메시지를 property로 담아준다.
@AllArgsConstructor
@Getter
public enum ErrorCode {

    DUPLICATE_LOGIN_ID("로그인 ID 중복 오류"),
    DUPLICATE_EMAIL("이메일 중복 오류"),
    INVALID_PASSWORD("유효하지 않은 비밀번호입니다."),
    INVALID_BIRTH_YEAR("출생년도는 1900년 이후, 현재 연도 이전이어야 합니다."),
    AGE_VALIDATION_FAILED("14세 인증 여부가 다릅니다."),
    INVALID_VERIFICATION_CODE("인증 코드가 일치하지 않습니다."),

    // 403 Forbidden - 인증되지 않은 접근
    EMAIL_NOT_VERIFIED("이메일 인증이 완료되지 않았습니다."),
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다"), // 진짜 알 수 없는 에러가 발생했을 때
    INVALID_REQUEST("잘못된 요청입니다."),

    USER_NOT_FOUND("해당되는 유저를 찾을 수 없습니다"),
    PASSWORD_MISMATCH("패스워드가 일치하지 않습니다."),
    UNAUTHORIZED_INVALID_TOKEN("유효하지 않은 JWT 토큰입니다"),

    CATEGORY_NOT_FOUND("해당 이름의 카테고리를 찾을 수 없습니다."),
    AUTHOR_NOT_FOUND("해당 필명의 작가를 찾을 수 없습니다."),
    PUBLISHER_NOT_FOUND("해당 이름의 출판사를 찾을 수 없습니다."),
    KEYWORD_NOT_FOUND("해당하는 키워드를 찾을 수 없습니다."),
    BOOK_NOT_FOUND("해당하는 책을 찾을 수 없습니다."),
    RATING_NOT_FOUND("해당 책에 대한 별점이 존재하지 않습니다."),
    REVIEW_NOT_FOUND("해당 리뷰가 존재하지 않습니다."),

    CART_ITEM_ALREADY_EXISTS("이미 장바구니에 추가된 항목입니다."),
    CART_ITEM_NOT_FOUND("장바구니 항목을 찾을 수 없습니다."),
    ITEM_NOT_OWNED_BY_USER("이 아이템은 다른 유저가 넣은 아이템입니다."),
    CART_EMPTY("해당 장바구니는 비어있습니다"),

    DISCOUNT_NOT_FOUND("해당 책의 할인률 정보가 없습니다"),
    EVENT_NOT_FOUND("해당 이벤트가 존재하지 않습니다");

    private final String message;
}
