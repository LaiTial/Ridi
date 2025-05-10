package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.VerificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailVerificationController {

    private final VerificationService verificationService;

    @PostMapping("/signup/verification-code/verify")
    public void verifyEmailCode(
            @RequestParam String code, @RequestParam String email) { // 인증 코드, 이메일 받음
        verificationService.checkVerificationCode(code, email);
    }
}
