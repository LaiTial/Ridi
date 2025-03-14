package org.controller;

import lombok.RequiredArgsConstructor;
import org.service.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoginController {

    //private final LoginService dMakerService; // LoginController가 LoginService를 가져다 쓰고 싶을 때 선언 필요

    // 회원가입 시 필수/선택인 약관 목록 반환
    @GetMapping("/login/list")
    public List<String> getAgreementList() { //List<AgreementDTO>
        return Arrays.asList("snow", "elsa", "Olaf");
    }

    // 이미 있는 ID인지, 아닌지 중복 체크하는 함수
    @GetMapping("/login/id-duplication-check")
    public void isIdDuplicated(Long id) {
        return;
    }
}
