package com.pablotj.restemailbridge.infrastructure;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.pablotj.restemailbridge")
@EnableJpaRepositories(basePackages = "com.pablotj.restemailbridge")
@EntityScan(basePackages = "com.pablotj.restemailbridge")
public class RestEmailBridgeTestApplication {
}