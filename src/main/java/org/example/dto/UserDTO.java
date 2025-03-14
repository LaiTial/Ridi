package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.type.Gender;
import org.example.utils.PasswordValidator;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements ValidationCheck{

    @NotBlank
    private String loginId; // 로그인 ID

    @NotBlank
    @Size(min = 8) // 최소 8글자 이상
    private String password; // 비밀번호

    @NotBlank
    @Email // 이메일 형식인지
    private String email; // 이메일

    @NotNull
    @Size(min = 1, max = 128) // 최소 1글자, 최대 128글자
    private String name; // 이름

    private Gender gender; // 성별 (MALE, FEMALE과 같은 Enum 값으로 처리)
    private List<AgreementDTO> agreed;     // 동의한 약관 목록
    private Integer birthYear; // 출생년도
    private Boolean overFourteen; // 14세 이상 인증 여부

    // ✅ 1. 패스워드 유효성 체크 (DB 조회 필요 x)
    public void validatePassword() {

        if(!PasswordValidator.isValid(password)) { // 유효하지 않으면
            throw new RidiException(ErrorCode.INVALID_PASSWORD); // 패스워드가 유효하지 않습니다
        }
    }

    // ✅ 2. 출생년도 유효성 체크
    public void validateBirthYear() {

        LocalDate currentDate = LocalDate.now(); // 현재 날짜 가져오기
        int currentYear = currentDate.getYear(); // 현재 연도 추출

        if(birthYear < 1900 || birthYear > currentYear) { // 1900년 생 이전 or 미래년도일 시
            throw new RidiException(ErrorCode.INVALID_BIRTH_YEAR); // 출생년도가 유효하지 않습니다.
        }
    }

    // ✅ 3. 14세 이상 여부 검증 메서드 추가
    public void validateAge() {
        LocalDate currentDate = LocalDate.now(); // 현재 날짜 가져오기
        int currentYear = currentDate.getYear(); // 현재 연도 추출

        if (overFourteen) { // 14세 이상이라 하고 14세 이하일 경우
            if (currentYear - birthYear < 14) {
                throw new RidiException(ErrorCode.AGE_VALIDATION_FAILED); // 14세 인증 실패
            }
        } else { // 14세 이하라면서 실제로 14세 이상일 경우
            if (currentYear - birthYear >= 14) {
                throw new RidiException(ErrorCode.AGE_VALIDATION_FAILED); // 14세 인증 실패
            }
        }
    }

    // ✅ 4. 모든 검증을 한 번에 실행하는 메서드 추가
    public void check() {
        validatePassword();
        validateBirthYear();
        validateAge();
    }
}
