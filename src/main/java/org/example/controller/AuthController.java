package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
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
    public void logout(@RequestHeader("Authorization") String authorizationHeader) { // 쿼리 스트링으로 중복 체크할 id 받는다.
        authenticationService.logout(authorizationHeader); // 로그아웃
    } // 객체 자체가 Authorization 인게 있다 그럼 객체 자체를 넣어준다. 스프링 시큐리티가 넣어줌.

    // 인증되는지 테스팅
    @GetMapping("/test")
    public void test() {
    }
}
