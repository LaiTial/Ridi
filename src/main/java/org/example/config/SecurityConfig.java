package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호를 안전하게 해싱하고 검증하기 위해 Bean으로 등록
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // CSRF 보호 비활성화
                .authorizeHttpRequests()
                .requestMatchers("/login/join").permitAll() // 회원가입 페이지 접근 허용
                //.requestMatchers("/login/ridi").authenticated() // /login/ridi 경로에 대해서 인증된 사용자만 접근 가능
                .anyRequest().permitAll() // 그 외 모든 경로는 인증 없이 접근 가능
                .and()
                .formLogin()
                .loginPage("/login/ridi") // 로그인 페이지 설정
                .permitAll() // 로그인 페이지는 누구나 접근 가능
                .and()
                .logout()
                .permitAll(); // 로그아웃도 누구나 가능

        return http.build(); // httpSecurity 객체 반환
    }
}
