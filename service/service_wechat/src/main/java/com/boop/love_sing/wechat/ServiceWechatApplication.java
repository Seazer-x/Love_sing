package com.boop.love_sing.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceWechatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceWechatApplication.class, args);
    }
}
