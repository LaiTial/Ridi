package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.LoginDTO;
import org.example.entity.Users;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.UserRepository;
import org.example.security.JwtTokenProvider;
import org.example.type.EmailVerifiedStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    // 자동으로 필요한 Reposiroty를 여기에 injection.
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Transactional(readOnly = true)
    public String authenticateUser(LoginDTO loginDTO) {

        // 1. 아이디를 통해 비밀번호 가져오기
        Users users = userRepository.findByLoginId(loginDTO.getLoginId())
                .orElseThrow(() -> new RidiException(ErrorCode.USER_NOT_FOUND)); // 유저 찾지 못했다

        // 2. 저장된 비밀번호와 맞는지 검증
        if(!passwordService.verifyPassword(loginDTO.getPassword(), users.getPassword())) {
            throw new RidiException(ErrorCode.PASSWORD_MISMATCH); // 패스워드 불일치
        }

        // 3. 이메일이 Verify 상태인지 검증
        if(users.getEmailVerified() != EmailVerifiedStatus.VERIFIED) {
            throw new RidiException(ErrorCode.EMAIL_NOT_VERIFIED);
        }

        // 4. JWT 토큰 생성해 Return
        String token = jwtTokenProvider.createToken(loginDTO.getLoginId());

        // 5. 생성한 JWT 토큰을 redis 저장
        redisService.saveData(loginDTO.getLoginId(), token);

        return token;
    }

    public void logout(String bearerToken) {

        // 1. Authorization 헤더에서 Bearer 토큰 추출
        String token = bearerToken.substring(7);  // "Bearer " 부분을 제거한 후 토큰만 추출

        // 2. 토큰 내의 사용자 ID 가져오기
        String username = jwtTokenProvider.getUsername(token);

        // 3. Redis 토큰 삭제 처리
        redisService.deleteData(username);
    }
}
