package org.example.dto;

import lombok.*;
import org.example.entity.Term;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TermDTO {

    private int version; // 현재 약관의 version
    private String title; // 약관명
    private String isRequired; // 약관의 필수 여부
    private String link; // 자세한 약관을 볼 수 있는 링크

    // builder 메소드
    public static TermDTO fromEntity(Term agreement) {
        return TermDTO.builder()
                .version(agreement.getTermVersionKey().getVersion()) // 버전 추가
                .title(agreement.getTermVersionKey().getTitle().getTitle()) // 제목 추가
                .isRequired(agreement.getIsRequired().getDescription())
                .link(agreement.getLink())
                .build();
    }

}
