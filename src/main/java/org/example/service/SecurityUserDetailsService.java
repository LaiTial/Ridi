package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Users;
import org.example.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Users user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new org.springframework.security.core.userdetails.User(
                user.getLoginId(),   // 로그인 ID
                user.getPassword(),  // 비밀번호
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())) // 권한
        );
    }
}
