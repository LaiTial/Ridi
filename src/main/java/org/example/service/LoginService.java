package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AgreementDTO;
import org.example.dto.TermDTO;
import org.example.dto.UserDTO;
import org.example.entity.Agreement;
import org.example.entity.Users;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.AgreementRepository;
import org.example.repository.TermRepository;
import org.example.repository.UserRepository;
import org.example.type.EmailVerifiedStatus;
import org.example.type.Role;
import org.example.utils.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {

    // 자동으로 필요한 Reposiroty를 여기에 injection.
    private final TermRepository termRepository;
    private final AgreementRepository agreementRepository;
    private final UserRepository userRepository;
    private final VerificationService verificationService;
    private final PasswordService passwordService;

    // 이메일 사용을 위한 설정
    private final EmailService emailService;

    // 약관 내용 리스트 반환
    @Transactional // 메소드 종료 시까지 DB 섹션 유지
    public List<TermDTO> getAllAgreements() {
        return termRepository.findLatestTerms() // groupby를 해서 제일 version이 높은 애만.
                .stream()
                .map(TermDTO::fromEntity) // Entity를 DTO로 변환
                .collect(Collectors.toList()); // 최종적으로 데이터를 모아서 원하는 형태(List, Set, Map 등)로 변환

    }

    // 이미 있는 ID인지, 아닌지 중복 체크하는 함수
    public void checkDuplicateId(String loginId) {
        if (userRepository.existsByLoginId(loginId)) { // 중복 체크
            throw new RidiException(ErrorCode.DUPLICATE_LOGIN_ID); // 중복된 ID입니다.
        }
    }

    // 회원가입하는 API
    @Transactional
    public void createUserAccount(UserDTO userDTO) {

        validate(userDTO); // 비즈니스 로직 검증

        // 1. 사용자 정보 저장
        Users users = Users.builder()
                .loginId(userDTO.getLoginId())
                .password(passwordService.hashPassword(userDTO.getPassword())) // 비밀번호 인코딩시킨 후 저장
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .birthYear(userDTO.getBirthYear())
                .gender(userDTO.getGender())
                .emailVerified(EmailVerifiedStatus.UNVERIFIED) // 기본적으로 인증되지 않은 사용자 설정
                .role(Role.USER) // 유저로 설정
                .build();

        userRepository.save(users); // 회원 테이블에 정보 저장

        // 2. 약관 내용 저장
        List<Agreement> agreements = userDTO.getAgreed()
                .stream()
                .map(agreementDTO -> AgreementDTO.fromEntity(agreementDTO, userDTO.getLoginId())) // 각 약관 Agreement Entity로 매핑
                .collect(Collectors.toList()); // 최종적으로 데이터를 모아서 원하는 형태(List, Set, Map 등)로 변환*/

        agreementRepository.saveAll(agreements); // 회원 테이블에 정보 저장

        // 3. 인증 코드 생성해 저장 및 이메일 전송
        String verificationCode = verificationService.generateAndSaveVerificationCode(userDTO.getEmail());
        emailService.sendVerificationEmail(userDTO.getEmail(), verificationCode);
    }

    private void validate(UserDTO userDTO){
        // 1. 아이디 중복 체크
        if (userRepository.existsByLoginId(userDTO.getLoginId())) { // 중복 체크
            throw new RidiException(ErrorCode.DUPLICATE_LOGIN_ID); // 중복된 ID입니다.
        }

        // 2. 이메일 중복 체크
        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RidiException(ErrorCode.DUPLICATE_EMAIL); // 중복된 email입니다.
        }
    }

}
