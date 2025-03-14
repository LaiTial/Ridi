package org.example.dto;

import lombok.*;
import org.example.entity.Term;
import org.example.type.RequiredStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgreementDTO {


    private int version; // 현재 약관의 version
    private String title; // 약관명
    private RequiredStatus isRequired; // 약관의 필수 여부
    private String link; // 자세한 약관을 볼 수 있는 링크

    // builder 메소드
    public static AgreementDTO fromEntity(Term agreement) {
        return AgreementDTO.builder()
                .version(agreement.getTermVersionKey().getVersion()) // 버전 추가
                .title(agreement.getTermVersionKey().getTitle()) // 제목 추가
                .isRequired(agreement.getIsRequired())
                .link(agreement.getLink())
                .build();
    }

}
