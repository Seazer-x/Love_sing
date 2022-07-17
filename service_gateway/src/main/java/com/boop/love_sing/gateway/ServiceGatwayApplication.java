package com.boop.love_sing.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceGatwayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceGatwayApplication.class, args);
    }
}
