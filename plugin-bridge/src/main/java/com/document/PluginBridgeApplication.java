package com.document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableDiscoveryClient
//@EnableFeignClients // Similar ComponentScan. Looks for interfaces
// declared with @FeignClient and will create a proxy class that implements it
public class PluginBridgeApplication {

    public static void main(String[] args) {
	SpringApplication.run(PluginBridgeApplication.class, args);
    }

}
