package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication // scanBase로 스캔 대상 조회
// 하위만 조회하기 때문에 다른 모듈에 분리되어 있으므로 지정해줘야한다.
@ComponentScan(basePackages = {
        "com.example.common",     // 공통 컴포넌트 (Service, Config 등)
        "com.example.batch"       // 배치 모듈 컴포넌트 (필요 시)
})
@EntityScan(basePackages = {
        "com.example.common.entity",  // 공통 엔티티
})
@EnableJpaRepositories(basePackages = {
        "com.example.common.repository",
})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
