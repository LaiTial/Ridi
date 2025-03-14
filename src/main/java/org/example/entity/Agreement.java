package org.example.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Agreement extends Base{ // Base 상속받아서 JPA Auditing 구현

    // UserID와 termVersion 묶어서 복합키로.
    @EmbeddedId
    private UserAgreementKey userAgreementKey; // 사용자ID, (약관 버전, 약관 제목)

    private Boolean agreement; // 동의 여부
}
