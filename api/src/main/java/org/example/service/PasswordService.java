package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

    // 비밀번호 해싱을 위한 객체 선언
    private final PasswordEncoder passwordEncoder;

    // 비밀번호 인코딩
    public String hashPassword(String password) {
        return passwordEncoder.encode(password); // 비밀번호 해시화 후 반환
    }

    // 비밀번호 검증
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
