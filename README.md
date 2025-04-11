# 리디북스 만들어보기

## 프로젝트 설명
이 프로젝트는 전자책으로 유명한 리디북스의 백엔드 서버를 재현하는 프로젝트.
본 프로젝트는 `Spring Boot`와 `MySQL`을 사용하여 구축되었으며, 사용자 관리, 약관 관리, 이메일 인증 등의 기능을 제공합니다.

## 사용한 기술 스택
- `JDK` :  `20`
- `Spring Boot` : `'3.4.4'`
- `Spring Dependency Management` :  `'1.1.7'`
- `JPA` : `'3.4.4'`
- `MySQL` : `'3.4.4'`
- `Spring Security` : `'3.4.4'`
- `JWT` : `'0.12.3'`
- `Redis` : `'3.4.4'`

## Docker 이미지 버전
- `MySQL` : `'8.0'`
- `Redis` : `'7.4.2'`

## 주요 기능
### 약관 목록 기능
-  약관의 `version`, `title`을 그룹 삼아 각 `version`별 최신 약관만 반환

### ID 중복 체크 기능
-  DB 조회해서 중복된 `ID`가 있는지 확인

### 회원 가입 기능
#### `validation`
- `ID` 중복 체크
- `email` 중복 체크
- `password`가 숫자, 영문자, 특수문자 중 2가지 이상 사용했는지 체크
- `birthYear` 유효성 체크
- `14세 인증` 여부 체크

#### `기능들`
- `BCryptPasswordEncoder`로 회원가입 시 DB에 저장되는 비밀번호 해싱
- 사용자 정보를 `DB`에 저장
- `SecureRandom`로 랜덤으로 안전한 인증 코드 생성
- `Redis`에 `email`과 `인증코드` 저장
- 사용자 이메일에 `email`과 `인증코드`로 메일 보내기

### 이메일 전송 기능
- 사용자가 입력한 `email`과 `인증코드`를 Redis 조회
- 맞을 시 사용자 이메일 `UNVERIFIED`에서 `VERIFIED`로 변경
- `My SQL 스케줄러`로 1시간 간격마다 인증되지 않은 사용자, 동의 여부 삭제

### 로그인 기능
- DB에 저장된 `ID`와 `Password`와 맞는지 검증
- 이메일이 `VERIFIED` 상태인지 검증
- `JWT` 토큰 생성해 `Redis`에 저장한 후, 사용자에게 전달
- 사용자가 페이지 접속 시 `JWT` 토큰으로 유효성 검사
- 발급한 JWT 토큰이 `Redis`에 사용 가능한 상태이고 인증을 통과하면 접근 가능
- 이를 `Spring Security`에 `JWT` 필터 등록해 자동화

### 로그아웃 기능
- `JWT` 토큰에서 사용자 ID 얻어오기
- `Redis`에 저장된 `ID`별 `JWT` 토큰 삭제

### 카테고리 기능
- 상위 카테고리와 하위 카테고리 간의 `계층 관계`를 지원
- 하위 카테고리를 포함한 `전체 트리 구조` 조회 가능

### 작가 (Author) 기능

- 새로운 `작가 (Author)` 생성

### 출판사 (Publisher) 기능

- 출판사 `이름(name)`으로 등록

### 책(Book) 기능

- 새로운 책 등록 기능 : `Category` 이름, `Author` 필명, `Publisher` 이름으로 연관 엔티티 조회 및 설정
_ 단행본과 연재본처럼 동일 ISBN이지만 구분이 필요한 경우 `SerialStatus(연재/완결)` 필드로 구분_
- `imageUrl`, `description`, `wishlistCount`, `rating` 등 추가 정보도 함께 저장


## 주요 변경 사항들
- Vault 서버로 민감한 정보 암호화
- Redis 조회 시 오류를 막기 위해 `Optional`로 `null`값 체크
- 카테고리 기능으로 `상위 카테고리`, `하위 카테고리` 계층화
- 새로운 Author `Create` 기능 추가
- 새로운 Publisher `Create` 기능 추가
- 새로운 Book `Create` 기능 추가
- `Rating`(별점)과 `Review`(리뷰) Entity 생성