SET GLOBAL event_scheduler = ON;

-- 인증번호 삭제 이벤트
DROP EVENT IF EXISTS delete_expired_verifications_codes;
CREATE EVENT delete_expired_verifications_codes
ON SCHEDULE EVERY 1 HOUR
DO
  DELETE FROM verification
  WHERE created_at < NOW() - INTERVAL 1 HOUR;

-- 미인증 사용자 삭제 이벤트
DROP EVENT IF EXISTS delete_unverified_users;
CREATE EVENT delete_unverified_users
ON SCHEDULE EVERY 1 HOUR
DO
  DELETE FROM users
  WHERE email_verified = 'UNVERIFIED' AND created_at < NOW() - INTERVAL 1 HOUR;