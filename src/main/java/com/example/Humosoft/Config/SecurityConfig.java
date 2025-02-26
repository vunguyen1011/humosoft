package com.example.Humosoft.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
@Component
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {  
	      httpSecurity.csrf(csrf -> csrf.disable());
	      httpSecurity
	          .authorizeHttpRequests(request -> request
	              .anyRequest().permitAll()  
	          );

	      return httpSecurity.build();
	  }

}
