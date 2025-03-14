use ridi;


-- `term` 테이블: 약관 관련 정보를 저장
-- 이 테이블은 약관 제목(`title`), 버전(`version`), 약관 필수 여부(`is_required`), 약관 링크(`link`)를 관리.
CREATE TABLE `term` (
   `title` varchar(255) NOT NULL COMMENT '약관 제목',
   `version` int NOT NULL COMMENT '약관 버전',
   `is_required` smallint DEFAULT NULL COMMENT '약관 필수 여부 (1: 필수, 0: 선택)',
   `link` varchar(255) DEFAULT NULL COMMENT '약관 링크',
   PRIMARY KEY (`title`, `version`)  -- 제목과 버전으로 복합 기본 키 설정
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- `users` 테이블: 사용자 정보 저장
-- 이 테이블은 사용자의 고유 ID(`id`), 이메일(`email`), 로그인 ID(`login_id`), 성별(`gender`), 이름(`name`), 비밀번호(`password`) 등의 정보를 관리.
CREATE TABLE `users` (
   `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT "고유 ID",
   `created_at` DATETIME(6) DEFAULT NULL COMMENT "생성일자",
   `updated_at` DATETIME(6) DEFAULT NULL COMMENT "수정일자",
   `birth_year` INT DEFAULT NULL COMMENT "출생연도",
   `email` VARCHAR(255) NOT NULL UNIQUE COMMENT "이메일",
   `email_verified` VARCHAR(255) NOT NULL COMMENT "이메일 인증 여부",
   `gender` VARCHAR(255) NOT NULL COMMENT "성별",
   `login_id` VARCHAR(128) NOT NULL UNIQUE COMMENT "로그인 ID",
   `name` VARCHAR(128) NOT NULL COMMENT "이름",
   `password` VARCHAR(255) NOT NULL COMMENT "비밀번호",
   PRIMARY KEY (`id`) -- ID 기본 키
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- `agreement` 테이블: 사용자 약관 동의 정보 저장
-- 이 테이블은 약관 제목(`title`), 버전(`version`), 사용자 ID(`user_id`), 약관 동의 여부(`agreement`) 등을 관리.
-- 사용자 ID는 `users` 테이블의 로그인 ID(`login_id`)와 연결.
CREATE TABLE `agreement` (
    `title` varchar(255) NOT NULL COMMENT '약관 제목',
    `version` int NOT NULL COMMENT '약관 버전',
    `user_id` varchar(128) NOT NULL COMMENT '사용자 ID (로그인 ID)',
    `created_at` datetime(6) DEFAULT NULL COMMENT '생성일자',
    `updated_at` datetime(6) DEFAULT NULL COMMENT '수정일자',
    `agreement` bit(1) DEFAULT NULL COMMENT '약관 동의 여부 (1: 동의, 0: 미동의)',
    PRIMARY KEY (`title`, `version`, `user_id`),  -- 제목, 버전, 사용자 ID로 복합 기본 키 설정
    FOREIGN KEY (`title`, `version`)
        REFERENCES `term` (`title`, `version`)  -- 약관 테이블의 제목과 버전 참조
        ON DELETE CASCADE  -- 삭제 시 관련 데이터도 함께 삭제
        ON UPDATE CASCADE,  -- 업데이트 시 관련 데이터도 함께 업데이트
    FOREIGN KEY (`user_id`)
        REFERENCES `users` (`login_id`)  -- 사용자 테이블의 로그인 ID 참조
        ON DELETE CASCADE  -- 삭제 시 관련 데이터도 함께 삭제
        ON UPDATE CASCADE  -- 업데이트 시 관련 데이터도 함께 업데이트
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- `verification` 테이블: 이메일 인증 코드 저장
-- 이 테이블은 이메일 인증을 위한 코드(`verification_code`)와 관련된 생성일자(`created_at`), 수정일자(`updated_at`) 등을 관리.
CREATE TABLE `verification` (
   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '고유 ID',
   `created_at` datetime(6) DEFAULT NULL COMMENT '생성일자',
   `updated_at` datetime(6) DEFAULT NULL COMMENT '수정일자',
   `email` varchar(255) NOT NULL COMMENT '이메일',
   `verification_code` varchar(8) NOT NULL COMMENT '이메일 인증 코드',
   PRIMARY KEY (`id`)  -- 고유 ID를 기본 키로 설정
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- `term` 테이블에 샘플 데이터 삽입 (약관 제목, 버전, 필수 여부, 링크)
INSERT INTO term (version, title, is_required, link) VALUES
(1, "TERMS_AGREEMENT", TRUE, 'https://example.com/privacy-policy'),
(1, "GENDER_BIRTH_INFO_CONSENT", FALSE, 'https://example.com/terms-of-service'),
(2, "GENDER_BIRTH_INFO_CONSENT", FALSE, 'https://example.com/terms-of-service'),
(3, "GENDER_BIRTH_INFO_CONSENT", FALSE, 'https://example.com/terms-of-service'),
(1, "RECEIVE_EVENT_NOTIFICATION", FALSE, 'https://example.com/terms-of-service'),
(1, "PERSONAL_INFO_AGREEMENT", TRUE, 'https://example.com/terms-of-service'),
(2, "PERSONAL_INFO_AGREEMENT", TRUE, 'https://example.com/terms-of-service');