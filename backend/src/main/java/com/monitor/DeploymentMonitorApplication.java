package com.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DeploymentMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeploymentMonitorApplication.class, args);
    }
} 