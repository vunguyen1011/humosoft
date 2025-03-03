package com.example.Humosoft.Config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.Humosoft.Model.Role;
import com.example.Humosoft.Repository.RoleRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {
	   @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	   @Bean
	   ApplicationRunner applicationRunner(RoleRepository roleRepository){
		 return args->{
			  Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
	               Role role = new Role();
	               role.setName("ROLE_ADMIN");
	               return roleRepository.save(role);
	           });

	           Role staffRole = roleRepository.findByName("ROLE_STAFF").orElseGet(() -> {
	               Role role = new Role();
	               role.setName("ROLE_STAFF");
	               return roleRepository.save(role);
	           });

	           Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
	               Role role = new Role();
	               role.setName("ROLE_USER");
	               return roleRepository.save(role);
	           });
		 };

	   }
}
