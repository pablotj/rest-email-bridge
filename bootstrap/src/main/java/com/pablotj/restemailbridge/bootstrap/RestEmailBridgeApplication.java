package com.pablotj.restemailbridge.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.pablotj.restemailbridge")
@EnableJpaRepositories(basePackages = {"com.pablotj.restemailbridge.infrastructure"})
@EntityScan(basePackages = {"com.pablotj.restemailbridge.infrastructure"})
public class RestEmailBridgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestEmailBridgeApplication.class, args);
    }
}
