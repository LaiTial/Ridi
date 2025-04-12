package org.example.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PublicationStatus {
    ACTIVE("출간 중"),           // 출간 중
    SUSPENDED("출간 일시 중지"),  // 출간 일시 중지
    CANCELLED("출간 중단");      // 출간 중단 (취소)

    private final String description;
}
