package org.example.repository;

import org.example.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends
        JpaRepository<Users, Long> {

    // loginId 중복 체크를 위한 메소드
    boolean existsByLoginId(String loginId);

    // 이메일 중복 체크를 위한 메소드
    boolean existsByEmail(String email);

    // 이메일로 해당하는 회원 정보 찾기
    Optional<Users> findByEmail(String email);

    // 로그인 ID를 기준으로 사용자를 찾는 메서드
    // loginID를 기준으로 사용자 조회
    Optional<Users> findByLoginId(String loginId);
}
