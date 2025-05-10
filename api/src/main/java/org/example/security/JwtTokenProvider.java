package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final String secretKeyString = "dGhpcyBpcyBhIHZlcnkgc3RyYXRlZyBzZWNyZXQga2V5IHRoYXQgY2FuIGJlIGluY29kZWQgd2VsbC4="; // 토큰 서명을 위한 비밀 키
    private final SecretKey secretKey = new SecretKeySpec((Base64.getDecoder().decode(secretKeyString.getBytes())), "HmacSHA256"); // HMAC SHA 알고리즘을 사용하여 JWT 서명 키 생성
    private final long validityInMilliseconds = 3600000; // 토큰의 유효시간(1시간, 분단위)

    public String createToken(String username) {

        // JWT Claims : 토큰에 포함된 데이터(정보)
        Claims claims = Jwts.claims().subject(username).build(); //

        // 토큰의 만료 시간 계산
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds); // 초 단위로 더해 토큰 만료 시간 계산

        // JWT 토큰 생성해 반환
        return Jwts.builder()
                .claims(claims) // JWT 생성 시작
                .issuedAt(now) // 발급 시간 설정
                .expiration(validity)  // 만료 시간 설정
                .signWith(secretKey) // 토큰을 서명 알고리즘으로 암호화
                .compact(); // JWT 문자열로 변환하여 반환
    }

    // JWT 토큰에서 사용자 정보 추출
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey) // secret key 설정
                .build()
                .parseSignedClaims(token) // 토큰의 서명을 검증하고, 파싱하여 claim 가져오기
                .getPayload()
                .getSubject(); // JWT의 사용자 식별 정보를 추출
    }

    // JWT 토큰 유효성 검사
    public boolean validateToken(String token) {

        try {
            Jws<Claims> claims = Jwts.parser()
                                    .verifyWith(secretKey) // secret key 설정
                                    .build()
                                    .parseSignedClaims(token); // 서명 검증
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (JwtException e) {
            throw new RidiException(ErrorCode.UNAUTHORIZED_INVALID_TOKEN);
        }
    }
}
