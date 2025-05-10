package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.LoginDTO;
import org.example.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    // 로그인을 하는 APi
    @PostMapping("/auth/login")
    public String login(
            @Valid @RequestBody LoginDTO loginDTO
            ) { // 쿼리 스트링으로 중복 체크할 id 받는다.
        return authenticationService.authenticateUser(loginDTO); // 로그인 후 JWT 토큰 반환
    }

    // 로그아웃을 하는 APi
    @GetMapping("/auth/logout")
    public void logout() {
        authenticationService.logout(); // 로그아웃
    }
}
