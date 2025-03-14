package org.example.repository;

import org.example.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository
        extends JpaRepository<Verification, Long> {

    // 해당 email과 인증코드가 테이블에 존재하는지 반환
    boolean existsByEmailAndVerificationCode(String email, String verificationCode);

    // 해당 이메일로 저장된 모든 인증 코드 삭제
    void deleteByEmail(String email);
}
