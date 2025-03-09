package com.example.Humosoft.DTO.Request;

import lombok.Data;

@Data
public class ForgotPasswordRequest {

	private String token;
	private String password;
	private String confirmPassword;

}
