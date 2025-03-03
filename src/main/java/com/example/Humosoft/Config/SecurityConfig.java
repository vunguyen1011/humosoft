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
	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {  
	      httpSecurity.csrf(csrf -> csrf.disable());
	      httpSecurity
	          .authorizeHttpRequests(request -> request
	              .anyRequest().permitAll()  
	          );
	      httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	      return httpSecurity.build();
	  }

}
