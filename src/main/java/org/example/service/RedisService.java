package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final long DEFAULT_TTL = 3600L; // 1시간 (3600초)

    // 데이터 저장
    public void saveData(String key, String value) {
        redisTemplate.opsForValue().set(key, value, DEFAULT_TTL, TimeUnit.SECONDS); // 1시간 TTL을 걸어 Redis에 저장
    }

    // 데이터 조회
    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    // 데이터 삭제
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
