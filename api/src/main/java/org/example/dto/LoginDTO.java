package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {

    @NotBlank
    private String loginId; // 로그인 ID

    @NotBlank
    @Size(min = 8) // 최소 8글자 이상
    private String password; // 비밀번호
}
