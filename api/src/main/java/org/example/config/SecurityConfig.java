package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.security.JwtAuthenticationFilter;
import org.example.security.JwtTokenProvider;
import org.example.service.RedisService;
import org.example.service.SecurityUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰 프로바이더 주입
    private final SecurityUserDetailsService securityUserDetailsService;
    private final RedisService redisService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호를 안전하게 해싱하고 검증하기 위해 Bean으로 등록
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, securityUserDetailsService, redisService); // JWT 필터를 등록하기 위해 Bean으로 등록
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                //cors의 기본 값은 모든 모든 origin, 헤더, HTTP 메서드(GET, POST, PUT, DELETE 등), 자격 증명을 허용하며, Preflight 요청의 최대 수명을 설정하지 않음
                //더 정교한 정책이 필요하다면 직접 custom하면 됩니다
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 보호 비활성화 (JWT 사용 시 보통 비활성화함)
                //세션 없이 stateless하게 설정
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 인증이 필요한 url 설정
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(
                                        "/login/**", // 회원가입 API 전부
                                        "/auth/login", // 로그인 API
                                        "/signup/verification-code/verify", // 이메일 인증 API
                                        "/books/**", // 조회 API
                                        "/api/**" // 키워드 조회 API
                                        ).permitAll() // 이 url은 인증 필요하지 않음
                                .requestMatchers(
                                        "/book/**", // 신규 책 등록+삭제 관련된 모든 API
                                        "/publishers/**", // 신규 출판사 등록+판매 중지 관련된 모든 API
                                        "/authors/**", // 신규 책 등록+판매 중지 관련된 모든 API,
                                        "/category/**", // 신규 카테고리 등록+삭제 관련 모든 API,
                                        "/keywords/**" // 신규 키워드 등록+삭제 관련 모든 API,
                                ).hasRole("ADMIN").
                                requestMatchers(
                                        "/ratings/**", // 별점 API
                                        "/reviews/**" // 댓글 API
                                ).hasRole("USER")
                                .anyRequest().authenticated() // 나머지 요청은 인증된 사용자만 접근 가능
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // JWT 인증 필터 추가

        return http.build(); // httpSecurity 객체 반환
    }
}
