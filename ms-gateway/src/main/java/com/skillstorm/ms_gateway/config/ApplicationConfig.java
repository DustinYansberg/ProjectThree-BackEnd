//package com.skillstorm.ms_gateway.config;
//
//import java.time.Duration;
//import java.util.List;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import lombok.RequiredArgsConstructor;
//
//@Configuration
//@RequiredArgsConstructor
//public class ApplicationConfig {
//
//    private final ApplicationProperties applicationProps;
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//	final var source = new UrlBasedCorsConfigurationSource();
//	final var config = new CorsConfiguration();
//	final var origins = List.of(applicationProps.clientOriginUrl());
//	final var headers = List.of(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE);
//	final var methods = List.of(HttpMethod.GET.name());
//	final var maxAge = Duration.ofSeconds(86400);
//
//	config.setAllowedOrigins(origins);
//	config.setAllowedHeaders(headers);
//	config.setAllowedMethods(methods);
//	config.setMaxAge(maxAge);
//
//	source.registerCorsConfiguration("/**", config);
//	return source;
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//	final var configSource = this.corsConfigurationSource();
//
//	return new CorsFilter(configSource);
//    }
//}
