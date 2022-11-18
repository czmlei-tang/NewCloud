package com.tang.newcloud.infrastructure.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InfrastructureApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfrastructureApiGatewayApplication.class, args);
    }
}