package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Agreement extends Base{ // Base 상속받아서 JPA Auditing 구현

    @EmbeddedId
    private UserAgreementKey userAgreementKey; // 사용자ID, (약관 버전, 약관 제목)

    private Boolean agreement; // 동의 여부
}
