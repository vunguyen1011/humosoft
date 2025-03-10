package com.example.Humosoft.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
@Component
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
   
    private final static String[] WHITE_URL = {"/auth/signin"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // Tắt CSRF nếu bạn đang làm việc với API và JWT
        httpSecurity.csrf(csrf -> csrf.disable());

        // Cấu hình quyền truy cập cho các endpoint công khai
        httpSecurity
                .authorizeHttpRequests(request -> request
                // Các URL trong WHITE_URL không cần xác thực
                .requestMatchers(WHITE_URL).permitAll()
                // Các yêu cầu còn lại cần phải xác thực
                .anyRequest().authenticated());

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

}
}
