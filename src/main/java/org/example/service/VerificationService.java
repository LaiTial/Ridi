package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Users;
import org.example.entity.Verification;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.UserRepository;
import org.example.repository.VerificationRepository;
import org.example.type.EmailVerifiedStatus;
import org.example.utils.RandomCharacterGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;

    // 인증 코드를 생성해 verification 테이블에 저장
    @Transactional
    public String generateAndSaveVerificationCode(String email) {
        String randomCode = RandomCharacterGenerator.generateRandomString(8); // 8자리 랜덤 코드 생성

        // 1. 해당 이메일로 지정된 인증코드가 있을 시 모두 삭제
        verificationRepository.deleteByEmail(email);

        // 2. 이메일과, 새로 생성한 인증코드 저장
        Verification verification = Verification.builder()
                    .email(email)
                    .verificationCode(randomCode)
                    .build();

        verificationRepository.save(verification);

        return randomCode;
    }

    @Transactional
    public void checkVerificationCode(String verifyCode, String email) {
        
        // 1. 이메일과 인증코드가 맞지 않으면 Error
        if(!verificationRepository.existsByEmailAndVerificationCode(email, verifyCode)) {
            throw new RidiException(ErrorCode.INVALID_VERIFICATION_CODE); // 인증코드가 일치하지 않음.
        } // 인증 코드도 여러번 틀릴 시 못하게.

        // 2. 맞을 시 이메일 인증됬다 상태 변경
        Users users = userRepository.findByEmail(email).orElseThrow(
                ()-> new RidiException(ErrorCode.EMAIL_NOT_VERIFIED)); // 없을 시 Error
        
        users.setEmailVerified(EmailVerifiedStatus.VERIFIED); // 이메일 인증됬다 설정

        // 3. verification 코드 테이블에서 삭제
        verificationRepository.deleteByEmail(email);
    }
}
