package org.example.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class UserAgreementKey implements Serializable {

    private String userId; // 사용자 ID
    private TermVersionKey termVersionKey; // 약관 버전, 제목이 묶어진 복합키 (FK)
}
