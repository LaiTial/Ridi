package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.example.type.RequiredStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 역할
    private Long id; // 약관의 ID

    // 생략 시 자동으로 @Column 어노테이션 지정, @Column은 객체 필드를 테이블 컬럼에 매핑
    private int version; // 현재 약관의 version
    private String title; // 약관명
    private RequiredStatus isRequired; // 약관의 필수 여부
    private String link; // 자세한 약관을 볼 수 있는 링크

}
