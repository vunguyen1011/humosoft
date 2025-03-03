package com.example.Humosoft.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Request.UserRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	@PostMapping
	public Apiresponse<User> create(@RequestBody UserRequest request) {
		User user=userService.create(request);
		return Apiresponse.<User>builder()
				.result(user)
				.build();
		
	}
	
}
