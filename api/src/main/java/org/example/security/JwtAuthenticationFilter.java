package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.service.RedisService;
import org.example.service.SecurityUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityUserDetailsService securityUserDetailsService;
    private final RedisService redisService;

    // 토큰 검증 및 인증 처리
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);  // HTTP 요청에서 JWT 토큰을 추출

        if (token != null && jwtTokenProvider.validateToken(token)) { // 토큰이 검증되었으면

            // 1. 사용자에게 발급한 JWT 토큰이 맞는지 검증
            String username = jwtTokenProvider.getUsername(token);

            if (!redisService.getData(username)
                    .map(redisToken -> redisToken.equals(token))
                    .orElse(false)) {
                throw new RidiException(ErrorCode.UNAUTHORIZED_INVALID_TOKEN); // 만료된 JWT 토큰
            }

            // 2. JWT가 유효하다면 인증 정보 생성
            UserDetails userDetails =  securityUserDetailsService.loadUserByUsername(username); // 사용자 정보 로드
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // Spring Security에서 인증 정보를 나타내는 객체를 생성

            // 3. Spring Security의 SecurityContext에 인증 정보를 설정, 이후 요청에서 해당 사용자가 인증된 상태로 처리
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response); // 요청이 처리되도록 호출
    }

    // 토큰 추출 메소드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // HTTP 요청의 Authorization 헤더에서 JWT 토큰을 추출

        // Bearer : JWT 토큰의 앞부분에 붙는 접두사
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) { // Authorization 헤더가 "Bearer "로 시작하는지 확인
            return bearerToken.substring(7);  // 실제 JWT 토큰만 반환
        }
        return null;
    }
}
