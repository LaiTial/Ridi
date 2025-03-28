package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Users;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.UserRepository;
import org.example.type.EmailVerifiedStatus;
import org.example.utils.RandomCharacterGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final UserRepository userRepository;
    private final RedisService redisService;

    // 인증 코드를 생성해 verification 테이블에 저장

    public String generateAndSaveVerificationCode(String email) {
        String randomCode = RandomCharacterGenerator.generateRandomString(8); // 8자리 랜덤 코드 생성

        // 1. 해당 이메일로 지정된 인증코드가 있을 시 모두 삭제
        redisService.deleteData(email);

        // 2. 이메일과, 새로 생성한 인증코드 저장
        redisService.saveData(email, randomCode);

        return randomCode;
    }

    @Transactional
    public void checkVerificationCode(String verifyCode, String email) {

        // 1. 이메일과 인증코드가 맞지 않으면 Error
        if(!redisService.getData(email).equals(verifyCode)) {
            throw new RidiException(ErrorCode.INVALID_VERIFICATION_CODE); // 인증코드가 일치하지 않음.
        }

        // 2. 맞을 시 이메일 인증됬다 상태 변경
        Users users = userRepository.findByEmail(email).orElseThrow(
                ()-> new RidiException(ErrorCode.EMAIL_NOT_VERIFIED)); // 없을 시 Error
        
        users.setEmailVerified(EmailVerifiedStatus.VERIFIED); // 이메일 인증됬다 설정

        // 3. redis 에서 verification 코드 삭제
        redisService.deleteData(email);

    }
}
