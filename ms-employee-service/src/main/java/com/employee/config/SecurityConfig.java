package com.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	http.httpBasic(Customizer.withDefaults());
	http.authorizeHttpRequests((reqs) -> {

	    reqs.requestMatchers(HttpMethod.POST, "/**").permitAll();
	    reqs.requestMatchers(HttpMethod.GET, "/**").permitAll();
	    reqs.requestMatchers(HttpMethod.PUT, "/**").permitAll();
	    reqs.requestMatchers(HttpMethod.DELETE, "/**").permitAll();

	})
//	.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
//		.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
		.csrf(csrf -> csrf.disable());

	return http.build();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//	CorsConfiguration configuration = new CorsConfiguration();
//
//	configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:9001"));
//	configuration.setAllowedMethods(List.of("GET", "POST"));
//	configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//
//	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//	source.registerCorsConfiguration("/**", configuration);
//
//	return source;
//    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//	return new WebMvcConfigurer() {
//	    @Override
//	    public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//			.allowedOrigins("http://localhost:4200", "http://localhost:9001")
//			.allowedHeaders(HttpHeaders.CONTENT_TYPE);
//	    }
//	};
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//	registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//		.allowedOrigins("http://localhost:4200", "http://localhost:9001")
//		.allowedHeaders(HttpHeaders.CONTENT_TYPE);
//    }
}
