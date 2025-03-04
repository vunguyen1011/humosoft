package com.example.Humosoft.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.*;
import com.example.Humosoft.DTO.Response.UserResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.UserMapper;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.UserRepository;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;

	public UserResponse create(UserRequest userRequest) {
		User user = userMapper.toUser(userRequest);
		userRepository.save(user);
		return userMapper.toUserResponse(user);
	}

	public void createLogin(UserLogin userLogin) throws MessagingException {

		User user = userRepository.findByEmail(userLogin.getEmail())
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
		String password = passwordEncoder.encode(userLogin.getPassword());
		user.setUsername(userLogin.getUsername());
		user.setPassword(password);
		userRepository.save(user);
		mailService.sendEmail(userLogin.getEmail(),userLogin.getUsername(), userLogin.getPassword());
		
	}
	public List<UserResponse> getAll(){
		return userRepository.findAll().stream()
				.map(userMapper::toUserResponse)
				.collect(Collectors.toList());
		
	}
}
