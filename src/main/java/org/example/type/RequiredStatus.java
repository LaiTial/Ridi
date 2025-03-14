package org.example.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RequiredStatus {
    REQUIRED, // 필수
    OPTIONAL; // 선택
}
