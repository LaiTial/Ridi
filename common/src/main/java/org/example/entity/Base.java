package org.example.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // 필드가 상속될 수 있도록 선언
@EntityListeners(AuditingEntityListener.class) // JPA Auditing을 하기 위한 설정
public class Base {

    // Auditing : Spring Data JPA 내부에 있는, 자동으로 값을 세팅해주는 기능
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt; // 신경쓰지 않아도 자동으로 업데이트
}
