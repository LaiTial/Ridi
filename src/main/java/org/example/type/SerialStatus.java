package org.example.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SerialStatus {
    ONGOING("연재"), // 연재중
    COMPLETED("단행본"); // 완결

    private final String description;
}
