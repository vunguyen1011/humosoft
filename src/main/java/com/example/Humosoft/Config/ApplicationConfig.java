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
			  Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
	               Role role = new Role();
	               role.setName("ADMIN");
	               return roleRepository.save(role);
	           });

	           Role staffRole = roleRepository.findByName("STAFF").orElseGet(() -> {
	               Role role = new Role();
	               role.setName("STAFF");
	               return roleRepository.save(role);
	           });

	           Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
	               Role role = new Role();
	               role.setName("USER");
	               return roleRepository.save(role);
	           });
		 };

	   }
}
