package org.example.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("일반 사용자"),
    ADMIN("관리자"),
    GUEST("게스트");

    private final String description;
}
