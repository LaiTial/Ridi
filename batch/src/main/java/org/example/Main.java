package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        excludeName = {
                "org.springframework.cloud.vault.config.VaultAutoConfiguration",
                "org.springframework.cloud.vault.config.VaultBootstrapConfiguration"
        },
        scanBasePackages = "org.example"
)

@EntityScan(basePackages = {
        "org.example.entity",  // 공통 엔티티
})
@EnableJpaRepositories(basePackages = {
        "org.example.repository"},
        considerNestedRepositories = false  // nested Redis repo 후보 무시
        )
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
