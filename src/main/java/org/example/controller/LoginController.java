package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.TermDTO;
import org.example.dto.UserDTO;
import org.example.service.LoginService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService; // LoginController가 LoginService를 가져다 쓰고 싶을 때 선언 필요

    // 회원가입 시 필수/선택인 약관 목록 반환
    @GetMapping("/login/list")
    public List<TermDTO> getAgreementList() {
        return loginService.getAllAgreements(); // 약관 목록 반환
    }

    // 이미 있는 ID인지, 아닌지 중복 체크하는 함수
    @GetMapping("/login/id-duplication-check")
    public void isIdDuplicated(@RequestParam String loginId) { // 쿼리 스트링으로 중복 체크할 id 받는다.
        loginService.checkDuplicateId(loginId); // 중복 체크
    }

    // 회원가입을 하는 API.
    @PutMapping("/login/join")
    public void createUser(
            @Valid @RequestBody UserDTO userDTO
            ) {

        userDTO.check(); // data validation
        loginService.createUserAccount(userDTO); // 회원가입하는 API
    }
}
