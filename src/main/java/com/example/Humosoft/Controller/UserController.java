package com.example.Humosoft.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Request.UserLogin;
import com.example.Humosoft.DTO.Request.UserRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.DTO.Response.UserResponse;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Service.UserService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	@PostMapping
	public Apiresponse<UserResponse> create(@RequestBody UserRequest request) {
		UserResponse user=userService.create(request);
		return Apiresponse.<UserResponse>builder()
				.result(user)
				.build();
		
	}
	@PostMapping("/create")
	public Apiresponse<Void>createLogin(@RequestBody UserLogin request) throws MessagingException{
		userService.createLogin(request);
		return Apiresponse.<Void>builder()
				.message(null)
				.build();
		
	}
	@GetMapping
	public Apiresponse<List<UserResponse>> findAll(){
		List<UserResponse> users=userService.getAll();
		return Apiresponse.<List<UserResponse>>builder()
				.result(users)
				.build();
	}
	
}
