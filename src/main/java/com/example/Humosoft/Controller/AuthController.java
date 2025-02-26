package com.example.Humosoft.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.Service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")

public class AuthController {
private final JwtService jwtService;
@PostMapping("/token/{username}")
Apiresponse<String> generateToken(@PathVariable String username) {
    String token = jwtService.generateAccessToken(username);
    return Apiresponse.<String>builder()
            .result(token)
            .build();

}

}
