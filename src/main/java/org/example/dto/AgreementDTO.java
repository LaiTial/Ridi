package org.example.dto;

import lombok.*;
import org.example.entity.Agreement;
import org.example.entity.Term;
import org.example.entity.TermVersionKey;
import org.example.entity.UserAgreementKey;
import org.example.type.TermType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AgreementDTO {

    private TermType title;       // 약관 제목
    private Integer version;    // 버전
    private Boolean isAgreed;   // 동의 여부

    // builder 메소드
    public static Agreement fromEntity(AgreementDTO agreementDTO, String loginID) {
        TermVersionKey termVersionKey = new TermVersionKey(agreementDTO.getVersion(), agreementDTO.getTitle());
        UserAgreementKey userAgreementKey = new UserAgreementKey(loginID, termVersionKey); // userId와 최신 약관의 TermVersionKey를 이용해서 복합키를 생성

        return Agreement.builder()
                .userAgreementKey(userAgreementKey)
                .agreement(agreementDTO.getIsAgreed())
                .build();
    }
}
