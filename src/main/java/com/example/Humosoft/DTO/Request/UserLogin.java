package com.example.Humosoft.DTO.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLogin {
	private String email;
	private String username;
	private String password;
}
