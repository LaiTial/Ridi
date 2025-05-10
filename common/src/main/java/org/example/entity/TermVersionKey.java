package org.example.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.type.TermType;

import java.io.Serializable;

// (version, title)을 묶어 복합(key) 생성
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class TermVersionKey implements Serializable {

    private Integer version; // 현재 약관의 version

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    private TermType title; // 약관명 (Enum 타입)
}
