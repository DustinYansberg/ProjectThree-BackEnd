package com.skillstorm.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configure security rules with specific request matchers
        return http
                .httpBasic(Customizer.withDefaults())  // Use HTTP Basic authentication
                .authorizeHttpRequests(reqs -> reqs
                    .requestMatchers("/employee").hasRole("ADMIN")
                    .requestMatchers("/employee/**").hasRole("ADMIN")  // Restrict access to /admin/** to ADMINs only
//                    .requestMatchers(HttpMethod.POST, "/coverage").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.PUT, "/coverage/**").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/coverage/**").hasRole("ADMIN")
                    .anyRequest().authenticated()  // All other requests require authentication
                )
                .csrf(csrf -> csrf.disable())  // Disable CSRF for simplicity (enable it for stateful sessions)
                .build();
    }

    @Bean
    JdbcUserDetailsManager userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);  // Load users from the database
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCrypt for password hashing
    }
}