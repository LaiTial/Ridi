package org.example.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.example.type.RequiredStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Term {

    @EmbeddedId
    private TermVersionKey termVersionKey; // 복합 키(PK)

    // 생략 시 자동으로 @Column 어노테이션 지정, @Column은 객체 필드를 테이블 컬럼에 매핑
    private RequiredStatus isRequired; // 약관의 필수 여부

    private String link; // 자세한 약관을 볼 수 있는 링크

}
