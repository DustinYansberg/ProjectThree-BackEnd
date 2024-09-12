package com.skillstorm.ms_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.skillstorm.controllers.FallbackController;

@SpringBootApplication
@ComponentScan(basePackageClasses = FallbackController.class)
@EnableDiscoveryClient
public class MsGatewayApplication {

    public static void main(String[] args) {
	SpringApplication.run(MsGatewayApplication.class, args);
    }

}
