package com.employee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
	registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedOrigins("*")
		.allowedHeaders(HttpHeaders.CONTENT_TYPE);
    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//	registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//		.allowedOrigins("http://localhost:4200", "http://localhost:9001")
//		.allowedHeaders(HttpHeaders.CONTENT_TYPE);
//    }

}