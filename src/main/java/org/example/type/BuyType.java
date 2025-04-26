package org.example.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BuyType {
    RENTAL("대여"),  // 대여
    OWNERSHIP("소장"); // 소장

    private final String description;
}
