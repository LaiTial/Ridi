package org.example.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RequiredStatus {
    REQUIRED("필수"), // 필수
    OPTIONAL("선택"); // 선택

    private final String description;
}
