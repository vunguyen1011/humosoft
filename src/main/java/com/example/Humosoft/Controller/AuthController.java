package com.example.Humosoft.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Request.LoginRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.Service.AuthService;
import com.example.Humosoft.Service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")

public class AuthController {
private final JwtService jwtService;
private final AuthService authService;
@PostMapping("/token/{username}")
Apiresponse<String> generateToken(@PathVariable String username) {
    String token = jwtService.generateAccessToken(username);
    return Apiresponse.<String>builder()
            .result(token)
            .build();

}
@PostMapping("/signin")
Apiresponse<String>signIn(@RequestBody LoginRequest loginRequest){
	String token =authService.signin(loginRequest);
	return Apiresponse.<String>builder()
			.message("login success")
			.result(token)
			.build();
}
}
