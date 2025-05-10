package org.example.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE("남자"),
    FEMALE("여자");

    private final String description;
}