package org.example.type;

import org.example.dto.UserDTO;

public enum TermType {

    TERMS_AGREEMENT("이용 약관", "termsAgreement"),
    PERSONAL_INFO_AGREEMENT("개인정보 수집 및 이용", "personalInfoAgreement"),
    RECEIVE_EVENT_NOTIFICATION("이벤트 및 혜택 알림", "receiveEventNotification"),
    GENDER_BIRTH_INFO_CONSENT("성별 및 생년 정보 제공", "genderBirthInfoConsent");

    private final String title;  // 약관 제목
    private final String fieldName;  // 동의 여부 필드 이름 (DTO와 일치)

    TermType(String title, String fieldName) {
        this.title = title;
        this.fieldName = fieldName;
    }

    public String getTitle() {
        return title;
    }

    public String getFieldName() {
        return fieldName;
    }

}
