package com.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients // Similar ComponentScan. Looks for interfaces
// declared with @FeignClient and will create a proxy class that implements it
public class MsEmployeeServiceApplication {

    public static void main(String[] args) {
	SpringApplication.run(MsEmployeeServiceApplication.class, args);
    }

}
