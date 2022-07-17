package com.boop.love_sing.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAppApplication.class, args);
    }
}
