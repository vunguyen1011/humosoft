package com.example.Humosoft.Service;

import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.UserRequest;
import com.example.Humosoft.Mapper.UserMapper;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserMapper userMapper;
	private final UserRepository userRepository;
public User create(UserRequest userRequest) {
	User user = userMapper.toUser(userRequest);
	 return userRepository.save(user);
}
public 
}
