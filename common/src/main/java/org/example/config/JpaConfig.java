package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // 설정값 파일
@EnableJpaAuditing // JPA Auditing 기능을 위한 어노테이션
public class JpaConfig {
}
